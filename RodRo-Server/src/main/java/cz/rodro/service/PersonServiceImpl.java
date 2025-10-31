package cz.rodro.service;

import cz.rodro.constant.Gender;
import cz.rodro.dto.*;
import cz.rodro.dto.mapper.PersonMapper;
import cz.rodro.dto.mapper.PersonSourceEvidenceMapper;
import cz.rodro.entity.*;
import cz.rodro.entity.repository.*;
import cz.rodro.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; // Ensure this specific import is used
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Validated
public class PersonServiceImpl implements PersonService {

    private final PersonMapper personMapper;
    private final FamilyRepository familyRepository;
    private final PersonRepository personRepository;
    private final SourceRepository sourceRepository;
    private final PersonSourceEvidenceRepository personSourceEvidenceRepository;
    private final LocationService locationService;
    private final PersonSourceEvidenceMapper personSourceEvidenceMapper;
    private final OccupationRepository occupationRepository;

    @Autowired
    public PersonServiceImpl(
            PersonMapper personMapper,
            FamilyRepository familyRepository,
            PersonRepository personRepository,
            SourceRepository sourceRepository,
            PersonSourceEvidenceRepository personSourceEvidenceRepository,
            LocationService locationService,
            PersonSourceEvidenceMapper personSourceEvidenceMapper,
            OccupationRepository occupationRepository
    ) {
        this.personMapper = personMapper;
        this.familyRepository = familyRepository;
        this.personRepository = personRepository;
        this.sourceRepository = sourceRepository;
        this.personSourceEvidenceRepository = personSourceEvidenceRepository;
        this.locationService = locationService;
        this.personSourceEvidenceMapper = personSourceEvidenceMapper;
        this.occupationRepository = occupationRepository;
    }

    /**
     * Adds a new person to the database.
     * Sets life event locations, parents, occupations, and source evidences.
     * Partial dates (year/month/day) are supported, including negative years (BC).
     *
     * @param personDTO DTO containing data for the new person
     * @return the saved person as a PersonDTO
     */
    @Override
    @Transactional
    public PersonDTO addPerson(PersonDTO personDTO) {
        PersonEntity personEntity = personMapper.toEntity(personDTO);

        // Set life event locations
        personEntity.setBirthPlace(fetchLocation(personDTO.getBirthPlace()));
        personEntity.setDeathPlace(fetchLocation(personDTO.getDeathPlace()));
        personEntity.setBurialPlace(fetchLocation(personDTO.getBurialPlace()));
        personEntity.setBaptismPlace(fetchLocation(personDTO.getBaptismPlace()));

        // Set parents
        if (personDTO.getMotherId() != null) {
            PersonEntity mother = fetchPersonById(personDTO.getMotherId());
            validateParentGender(mother, Gender.FEMALE, "Mother");
            personEntity.setMother(mother);
        }
        if (personDTO.getFatherId() != null) {
            PersonEntity father = fetchPersonById(personDTO.getFatherId());
            validateParentGender(father, Gender.MALE, "Father");
            personEntity.setFather(father);
        }

        // Set collections
        personEntity.setOccupations(buildOccupationEntities(personDTO.getOccupations(), personEntity));
        personEntity.setSourceEvidences(buildSourceEvidenceEntities(personDTO.getSourceEvidences(), personEntity));

        PersonEntity saved = personRepository.save(personEntity);
        return personMapper.toDTO(saved);
    }

    /**
     * Retrieves a person by their ID.
     *
     * @param personId ID of the person
     * @return PersonDTO representing the person
     * @throws NotFoundException if no person with the given ID exists
     */
    @Override
    @Transactional(readOnly = true)
    public PersonDTO getPerson(long personId) {
        return personMapper.toDTO(fetchPersonById(personId));
    }

    /**
     * Removes a person by their ID.
     *
     * @param personId ID of the person to remove
     * @throws NotFoundException if the person does not exist
     */
    @Override
    @Transactional
    public void removePerson(long personId) {
        personRepository.delete(fetchPersonById(personId));
    }

    /**
     * Retrieves a paginated, optionally filtered and sorted list of persons.
     * Supports search by name or other criteria as implemented in repository.
     *
     * @param searchTerm search string for filtering
     * @param pageable   pagination and sorting information
     * @return Page of PersonListProjection
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PersonListProjection> getAllPersons(String searchTerm, Pageable pageable) {
        return personRepository.findAllPersonsProjected(searchTerm, pageable);
    }

    /**
     * Updates an existing person.
     * Updates life event locations, parents, occupations, source evidences, and other fields.
     * Partial dates are supported.
     *
     * @param id        ID of the person to update
     * @param personDTO DTO containing updated data
     * @return updated PersonDTO
     * @throws NotFoundException if the person does not exist
     */
    @Override
    @Transactional
    public PersonDTO updatePerson(Long id, PersonDTO personDTO) {
        PersonEntity existing = fetchPersonById(id);

        // Update life event locations safely
        existing.setBirthPlace(fetchLocationOrKeep(personDTO.getBirthPlace(), existing.getBirthPlace()));
        existing.setDeathPlace(fetchLocationOrKeep(personDTO.getDeathPlace(), existing.getDeathPlace()));
        existing.setBurialPlace(fetchLocationOrKeep(personDTO.getBurialPlace(), existing.getBurialPlace()));
        existing.setBaptismPlace(fetchLocationOrKeep(personDTO.getBaptismPlace(), existing.getBaptismPlace()));

        // Update parents
        if (personDTO.getMotherId() != null) {
            PersonEntity mother = fetchPersonById(personDTO.getMotherId());
            validateParentGender(mother, Gender.FEMALE, "Mother");
            existing.setMother(mother);
        } else {
            existing.setMother(null);
        }
        if (personDTO.getFatherId() != null) {
            PersonEntity father = fetchPersonById(personDTO.getFatherId());
            validateParentGender(father, Gender.MALE, "Father");
            existing.setFather(father);
        } else {
            existing.setFather(null);
        }

        // Clear and rebuild collections
        existing.getOccupations().clear();
        existing.setOccupations(buildOccupationEntities(personDTO.getOccupations(), existing));

        existing.getSourceEvidences().clear();
        existing.setSourceEvidences(buildSourceEvidenceEntities(personDTO.getSourceEvidences(), existing));

        // Map other fields
        personMapper.updatePersonEntity(personDTO, existing);

        return personMapper.toDTO(personRepository.save(existing));
    }

    /**
     * Retrieves all families where the given person is the male spouse.
     *
     * @param personId ID of the person
     * @return list of FamilyEntity
     */
    @Override
    @Transactional(readOnly = true)
    public List<FamilyEntity> getSpousesAsMale(Long personId) {
        return familyRepository.findBySpouseMaleId(personId);
    }

    /**
     * Retrieves all families where the given person is the female spouse.
     *
     * @param personId ID of the person
     * @return list of FamilyEntity
     */
    @Override
    @Transactional(readOnly = true)
    public List<FamilyEntity> getSpousesAsFemale(Long personId) {
        return familyRepository.findBySpouseFemaleId(personId);
    }

    /**
     * Retrieves all families where the person is a spouse.
     *
     * @param personId ID of the person
     * @return combined list of FamilyEntity
     */
    @Override
    @Transactional(readOnly = true)
    public List<FamilyEntity> getSpouses(Long personId) {
        List<FamilyEntity> list = new ArrayList<>(getSpousesAsMale(personId));
        list.addAll(getSpousesAsFemale(personId));
        return list;
    }

    /**
     * Retrieves all source evidences linked to the person.
     *
     * @param personId ID of the person
     * @return list of PersonSourceEvidenceDTO
     */
    @Override
    @Transactional(readOnly = true)
    public List<PersonSourceEvidenceDTO> getSourceEvidences(Long personId) {
        return personSourceEvidenceRepository.findByPersonId(personId)
                .stream()
                .map(e -> {
                    PersonSourceEvidenceDTO dto = personSourceEvidenceMapper.toDTO(e);
                    if (e.getPerson() != null)
                        dto.setPersonName(e.getPerson().getGivenName() + " " + e.getPerson().getSurname());
                    if (e.getSource() != null)
                        dto.setSourceName(e.getSource().getTitle());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Retrieves siblings (persons sharing both mother and father) excluding the given person.
     *
     * @param personId ID of the person
     * @return list of PersonDTO representing siblings
     */
    @Override
    @Transactional(readOnly = true)
    public List<PersonDTO> getSiblings(Long personId) {
        PersonDTO person = getPerson(personId);
        if (person.getFatherId() == null || person.getMotherId() == null)
            return Collections.emptyList();

        return personRepository.findByFatherIdAndMotherId(person.getFatherId(), person.getMotherId()).stream()
                .filter(p -> !p.getId().equals(personId))
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ------------------- Private Helpers -------------------

    /**
     * Fetches a person by ID or throws NotFoundException.
     *
     * @param id person ID
     * @return PersonEntity
     */
    private PersonEntity fetchPersonById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person with ID " + id + " not found"));
    }

    /**
     * Fetches a location by DTO or returns null if DTO is null.
     *
     * @param dto LocationDTO
     * @return LocationEntity or null
     */
    private LocationEntity fetchLocation(LocationDTO dto) {
        if (dto == null || dto.getId() == null) return null;
        return locationService.fetchLocationById(dto.getId(), "Location");
    }

    /**
     * Fetches a location by DTO, or keeps the current value if DTO is null.
     *
     * @param dto     LocationDTO
     * @param current existing LocationEntity
     * @return LocationEntity
     */
    private LocationEntity fetchLocationOrKeep(LocationDTO dto, LocationEntity current) {
        if (dto == null || dto.getId() == null) return current;
        return locationService.fetchLocationById(dto.getId(), "Location");
    }

    /**
     * Validates that the parent has the expected gender.
     *
     * @param parent   PersonEntity
     * @param expected expected Gender
     * @param label    "Mother" or "Father"
     */
    private void validateParentGender(PersonEntity parent, Gender expected, String label) {
        if (parent.getGender() != null && parent.getGender() != expected)
            throw new IllegalArgumentException(label + " must be " + expected);
    }

    /**
     * Builds PersonOccupationEntity list from DTOs and associates them with a person.
     *
     * @param dtos   list of PersonOccupationDTO
     * @param person PersonEntity
     * @return list of PersonOccupationEntity
     */
    private List<PersonOccupationEntity> buildOccupationEntities(List<PersonOccupationDTO> dtos, PersonEntity person) {
        if (dtos == null) return new ArrayList<>();
        List<PersonOccupationEntity> list = new ArrayList<>();
        for (PersonOccupationDTO dto : dtos) {
            if (dto.getOccupationId() == null) continue;
            occupationRepository.findById(dto.getOccupationId()).ifPresent(occupation -> {
                PersonOccupationEntity entity = new PersonOccupationEntity();
                entity.setPerson(person);
                entity.setOccupation(occupation);
                entity.setStartYear(dto.getStartYear());
                entity.setEndYear(dto.getEndYear());
                list.add(entity);
            });
        }
        return list;
    }

    /**
     * Builds PersonSourceEvidenceEntity list from DTOs and associates them with a person.
     *
     * @param dtos   list of PersonSourceEvidenceDTO
     * @param person PersonEntity
     * @return list of PersonSourceEvidenceEntity
     */
    private List<PersonSourceEvidenceEntity> buildSourceEvidenceEntities(List<PersonSourceEvidenceDTO> dtos, PersonEntity person) {
        if (dtos == null) return new ArrayList<>();
        List<PersonSourceEvidenceEntity> list = new ArrayList<>();
        for (PersonSourceEvidenceDTO dto : dtos) {
            if (dto.getSourceId() == null) continue;
            sourceRepository.findById(dto.getSourceId()).ifPresent(source -> {
                PersonSourceEvidenceEntity entity = new PersonSourceEvidenceEntity();
                entity.setPerson(person);
                entity.setSource(source);
                entity.setPersonName(person.getGivenName() + " " + person.getSurname());
                entity.setSourceName(source.getTitle());
                list.add(entity);
            });
        }
        return list;
    }


    public List<PersonInLocationDTO> findPeopleByEvent(Long locationId, String eventType) {
        switch (eventType.toLowerCase()) {
            case "births":
                return personRepository.findByBirthPlaceId(locationId);
            case "deaths":
                return personRepository.findByDeathPlaceId(locationId);
            case "burials":
                return personRepository.findByBurialPlaceId(locationId);
            case "baptisms":
                return personRepository.findByBaptismPlaceId(locationId);
            default:
                // Handle unknown event type, perhaps throw an exception or return empty list
                return Collections.emptyList();
        }
    }
}
