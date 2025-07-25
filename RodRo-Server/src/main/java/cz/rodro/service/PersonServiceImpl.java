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

import java.util.*;
import java.util.stream.Collectors;

@Service
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
            OccupationRepository occupationRepository) {

        this.personMapper = personMapper;
        this.familyRepository = familyRepository;
        this.personRepository = personRepository;
        this.sourceRepository = sourceRepository;
        this.personSourceEvidenceRepository = personSourceEvidenceRepository;
        this.locationService = locationService;
        this.personSourceEvidenceMapper = personSourceEvidenceMapper;
        this.occupationRepository = occupationRepository;
    }

    @Override
    @Transactional
    public PersonDTO addPerson(PersonDTO personDTO) {
        LocationEntity birthPlace = safeFetchLocation(personDTO.getBirthPlace(), "Birth Place");
        LocationEntity deathPlace = safeFetchLocation(personDTO.getDeathPlace(), "Death Place");
        LocationEntity burialPlace = safeFetchLocation(personDTO.getBurialPlace(), "Burial Place");
        LocationEntity baptizationPlace = safeFetchLocation(personDTO.getBaptizationPlace(), "Baptization Place");

        PersonEntity personEntity = personMapper.toEntity(personDTO);
        personEntity.setBirthPlace(birthPlace);
        personEntity.setDeathPlace(deathPlace);
        personEntity.setBurialPlace(burialPlace);
        personEntity.setBaptizationPlace(baptizationPlace);

        if (personDTO.getMother() != null && personDTO.getMother().getId() != null) {
            PersonEntity mother = fetchPersonById(personDTO.getMother().getId());
            validateParentGender(mother, Gender.FEMALE, "Mother");
            personEntity.setMother(mother);
        }
        if (personDTO.getFather() != null && personDTO.getFather().getId() != null) {
            PersonEntity father = fetchPersonById(personDTO.getFather().getId());
            validateParentGender(father, Gender.MALE, "Father");
            personEntity.setFather(father);
        }

        List<PersonOccupationEntity> newOccupations = safeFetchOccupations(personDTO.getOccupations(), personEntity);
        Set<Long> occupationIds = new HashSet<>();
        List<PersonOccupationEntity> uniqueOccupations = new ArrayList<>();
        for (PersonOccupationEntity occ : newOccupations) {
            Long occId = occ.getOccupation().getId();
            if (occId != null && occupationIds.add(occId)) {
                uniqueOccupations.add(occ);
            }
        }
        personEntity.setOccupations(uniqueOccupations);

        List<PersonSourceEvidenceEntity> newEvidences = safeFetchSourceEvidences(personDTO.getSourceEvidences(), personEntity);
        Set<Long> evidenceSourceIds = new HashSet<>();
        List<PersonSourceEvidenceEntity> uniqueEvidences = new ArrayList<>();
        for (PersonSourceEvidenceEntity ev : newEvidences) {
            Long srcId = ev.getSource().getId();
            if (srcId != null && evidenceSourceIds.add(srcId)) {
                uniqueEvidences.add(ev);
            }
        }
        personEntity.setSourceEvidences(uniqueEvidences);

        PersonEntity savedPerson = personRepository.save(personEntity);

        return personMapper.toDTO(savedPerson);
    }

    @Override
    @Transactional(readOnly = true)
    public PersonDTO getPerson(long personId) {
        return personMapper.toDTO(fetchPersonById(personId));
    }

    @Override
    @Transactional
    public void removePerson(long personId) {
        PersonEntity person = fetchPersonById(personId);
        personRepository.delete(person);
    }

    /**
     * Retrieves a paginated, filtered, and sorted list of persons.
     * This method now uses the PersonListProjection for optimized data transfer.
     *
     * @param searchTerm An optional term to filter by given name, surname, or identification number.
     * @param pageable   Pagination and sorting information.
     * @return A Page of PersonListProjection.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PersonListProjection> getAllPersons(String searchTerm, Pageable pageable) {
        return personRepository.findAllPersonsProjected(searchTerm, pageable);
    }

    @Override
    @Transactional
    public PersonDTO updatePerson(Long id, PersonDTO personDTO) {
        PersonEntity existingPerson = fetchPersonById(id);

        LocationEntity birthPlace = safeFetchLocation(personDTO.getBirthPlace(), "Birth Place");
        LocationEntity deathPlace = safeFetchLocation(personDTO.getDeathPlace(), "Death Place");
        LocationEntity burialPlace = safeFetchLocation(personDTO.getBurialPlace(), "Burial Place");
        LocationEntity baptizationPlace = safeFetchLocation(personDTO.getBaptizationPlace(), "Baptization Place");

        personMapper.updatePersonEntity(personDTO, existingPerson);
        existingPerson.setBirthPlace(birthPlace);
        existingPerson.setDeathPlace(deathPlace);
        existingPerson.setBurialPlace(burialPlace);
        existingPerson.setBaptizationPlace(baptizationPlace);

        if (personDTO.getMother() != null && personDTO.getMother().getId() != null) {
            PersonEntity mother = fetchPersonById(personDTO.getMother().getId());
            validateParentGender(mother, Gender.FEMALE, "Mother");
            existingPerson.setMother(mother);
        } else {
            existingPerson.setMother(null);
        }
        if (personDTO.getFather() != null && personDTO.getFather().getId() != null) {
            PersonEntity father = fetchPersonById(personDTO.getFather().getId());
            validateParentGender(father, Gender.MALE, "Father");
            existingPerson.setFather(father);
        } else {
            existingPerson.setFather(null);
        }

        List<PersonOccupationEntity> newOccupations = safeFetchOccupations(personDTO.getOccupations(), existingPerson);
        Set<Long> newOccupationIds = newOccupations.stream()
                .map(o -> o.getOccupation().getId())
                .collect(Collectors.toSet());

        existingPerson.getOccupations().removeIf(
                po -> !newOccupationIds.contains(po.getOccupation().getId())
        );

        Set<Long> existingOccupationIds = existingPerson.getOccupations().stream()
                .map(po -> po.getOccupation().getId())
                .collect(Collectors.toSet());
        for (PersonOccupationEntity newOcc : newOccupations) {
            if (!existingOccupationIds.contains(newOcc.getOccupation().getId())) {
                existingPerson.getOccupations().add(newOcc);
            }
        }

        List<PersonSourceEvidenceEntity> newEvidences = safeFetchSourceEvidences(personDTO.getSourceEvidences(), existingPerson);
        Set<Long> newEvidenceSourceIds = newEvidences.stream()
                .map(e -> e.getSource().getId())
                .collect(Collectors.toSet());

        existingPerson.getSourceEvidences().removeIf(
                se -> !newEvidenceSourceIds.contains(se.getSource().getId())
        );

        Set<Long> existingEvidenceSourceIds = existingPerson.getSourceEvidences().stream()
                .map(se -> se.getSource().getId())
                .collect(Collectors.toSet());
        for (PersonSourceEvidenceEntity newEv : newEvidences) {
            if (!existingEvidenceSourceIds.contains(newEv.getSource().getId())) {
                existingPerson.getSourceEvidences().add(newEv);
            }
        }

        PersonEntity savedUpdatedPerson = personRepository.save(existingPerson);

        return personMapper.toDTO(savedUpdatedPerson);
    }

    private void validateParentGender(PersonEntity parent, Gender expectedGender, String parentType) {
        if (parent != null && parent.getGender() != null && !parent.getGender().equals(expectedGender)) {
            throw new IllegalArgumentException(parentType + " must be " + expectedGender + ", but was " + parent.getGender());
        }
    }

    private PersonEntity fetchPersonById(long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person with id " + id + " wasn't found in the database."));
    }

    private LocationEntity safeFetchLocation(LocationDTO locationDTO, String fieldName) {
        if (locationDTO == null || locationDTO.getId() == null) {
            return null;
        }
        return locationService.fetchLocationById(locationDTO.getId(), fieldName);
    }

    private List<PersonOccupationEntity> safeFetchOccupations(List<PersonOccupationDTO> occupationDTOs, PersonEntity person) {
        if (occupationDTOs == null) return new ArrayList<>();
        List<PersonOccupationEntity> occupationEntities = new ArrayList<>();
        for (PersonOccupationDTO dto : occupationDTOs) {
            if (dto.getOccupationId() == null) continue;
            OccupationEntity occupation = occupationRepository.findById(dto.getOccupationId()).orElse(null);
            if (occupation == null) continue;
            PersonOccupationEntity entity = new PersonOccupationEntity();
            entity.setPerson(person);
            entity.setOccupation(occupation);
            entity.setOccupationStartDate(dto.getStartDate());
            entity.setOccupationEndDate(dto.getEndDate());
            occupationEntities.add(entity);
        }
        return occupationEntities;
    }

    private List<PersonSourceEvidenceEntity> safeFetchSourceEvidences(List<PersonSourceEvidenceDTO> evidenceDTOs, PersonEntity person) {
        if (evidenceDTOs == null) return new ArrayList<>();
        List<PersonSourceEvidenceEntity> evidenceEntities = new ArrayList<>();
        for (PersonSourceEvidenceDTO dto : evidenceDTOs) {
            if (dto.getSourceId() == null) continue;
            SourceEntity source = sourceRepository.findById(dto.getSourceId()).orElse(null);
            if (source == null) continue;
            PersonSourceEvidenceEntity entity = new PersonSourceEvidenceEntity();
            entity.setPerson(person);
            entity.setSource(source);
            entity.setPersonName(person.getGivenName() + " " + person.getGivenSurname());
            entity.setSourceName(source.getSourceTitle());
            evidenceEntities.add(entity);
        }
        return evidenceEntities;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FamilyEntity> getSpousesAsMale(Long personId) {
        return familyRepository.findBySpouseMaleId(personId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FamilyEntity> getSpousesAsFemale(Long personId) {
        return familyRepository.findBySpouseFemaleId(personId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FamilyEntity> getSpouses(Long personId) {
        List<FamilyEntity> spouses = new ArrayList<>(getSpousesAsMale(personId));
        spouses.addAll(getSpousesAsFemale(personId));
        return spouses;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonSourceEvidenceDTO> getSourceEvidences(Long personId) {
        return personSourceEvidenceRepository.findByPersonId(personId)
                .stream()
                .map(evidence -> {
                    PersonSourceEvidenceDTO dto = personSourceEvidenceMapper.toDTO(evidence);
                    if (evidence.getPerson() != null) {
                        dto.setPersonName(evidence.getPerson().getGivenName() + " " + evidence.getPerson().getGivenSurname());
                    }
                    if (evidence.getSource() != null) {
                        dto.setSourceName(evidence.getSource().getSourceTitle());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonDTO> getSiblings(Long personId) {
        PersonDTO person = getPerson(personId);
        if (person == null) {
            throw new NotFoundException("Person not found");
        }

        Long fatherId = person.getFather() != null ? person.getFather().getId() : null;
        Long motherId = person.getMother() != null ? person.getMother().getId() : null;

        if (fatherId == null || motherId == null) {
            return Collections.emptyList();
        }

        // Assuming your repository can query by parents
        List<PersonEntity> siblingEntities = personRepository.findByFatherIdAndMotherId(fatherId, motherId);

        // Filter out the person itself
        return siblingEntities.stream()
                .filter(sibling -> !sibling.getId().equals(personId))
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }
}
