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
// Updated Location fetching
        LocationEntity birthPlace = safeFetchLocation(
                personDTO.getBirthPlace() != null ? personDTO.getBirthPlace().getId() : null,
                "Birth Place"
        );
        LocationEntity deathPlace = safeFetchLocation(
                personDTO.getDeathPlace() != null ? personDTO.getDeathPlace().getId() : null,
                "Death Place"
        );
        LocationEntity burialPlace = safeFetchLocation(
                personDTO.getBurialPlace() != null ? personDTO.getBurialPlace().getId() : null,
                "Burial Place"
        );
        LocationEntity baptizationPlace = safeFetchLocation(
                personDTO.getBaptizationPlace() != null ? personDTO.getBaptizationPlace().getId() : null,
                "Baptization Place"
        );

        PersonEntity personEntity = personMapper.toEntity(personDTO);
        personEntity.setBirthPlace(birthPlace);
        personEntity.setDeathPlace(deathPlace);
        personEntity.setBurialPlace(burialPlace);
        personEntity.setBaptizationPlace(baptizationPlace);

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

        personEntity.setOccupations(buildOccupationEntities(personDTO.getOccupations(), personEntity));
        personEntity.setSourceEvidences(buildSourceEvidenceEntities(personDTO.getSourceEvidences(), personEntity));

        PersonEntity saved = personRepository.save(personEntity);
        return personMapper.toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public PersonDTO getPerson(long personId) {
        return personMapper.toDTO(fetchPersonById(personId));
    }

    @Override
    @Transactional
    public void removePerson(long personId) {
        personRepository.delete(fetchPersonById(personId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PersonListProjection> getAllPersons(String searchTerm, Pageable pageable) {
        return personRepository.findAllPersonsProjected(searchTerm, pageable);
    }

    @Override
    @Transactional
    public PersonDTO updatePerson(Long id, PersonDTO personDTO) {
        PersonEntity existing = fetchPersonById(id);

// Updated Location fetching
        LocationEntity birthPlace = safeFetchLocation(
                personDTO.getBirthPlace() != null ? personDTO.getBirthPlace().getId() : null,
                "Birth Place"
        );
        LocationEntity deathPlace = safeFetchLocation(
                personDTO.getDeathPlace() != null ? personDTO.getDeathPlace().getId() : null,
                "Death Place"
        );
        LocationEntity burialPlace = safeFetchLocation(
                personDTO.getBurialPlace() != null ? personDTO.getBurialPlace().getId() : null,
                "Burial Place"
        );
        LocationEntity baptizationPlace = safeFetchLocation(
                personDTO.getBaptizationPlace() != null ? personDTO.getBaptizationPlace().getId() : null,
                "Baptization Place"
        );

        personMapper.updatePersonEntity(personDTO, existing);
        existing.setBirthPlace(birthPlace);
        existing.setDeathPlace(deathPlace);
        existing.setBurialPlace(burialPlace);
        existing.setBaptizationPlace(baptizationPlace);

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

        // Update occupations
        List<PersonOccupationEntity> newOccupations = buildOccupationEntities(personDTO.getOccupations(), existing);
        Set<Long> newOccIds = newOccupations.stream().map(o -> o.getOccupation().getId()).collect(Collectors.toSet());
        existing.getOccupations().removeIf(o -> !newOccIds.contains(o.getOccupation().getId()));

        Set<Long> existingOccIds = existing.getOccupations().stream()
                .map(o -> o.getOccupation().getId())
                .collect(Collectors.toSet());
        for (PersonOccupationEntity occ : newOccupations) {
            if (!existingOccIds.contains(occ.getOccupation().getId())) {
                existing.getOccupations().add(occ);
            }
        }

        // Update evidences
        List<PersonSourceEvidenceEntity> newEvidences = buildSourceEvidenceEntities(personDTO.getSourceEvidences(), existing);
        Set<Long> newSrcIds = newEvidences.stream().map(e -> e.getSource().getId()).collect(Collectors.toSet());
        existing.getSourceEvidences().removeIf(se -> !newSrcIds.contains(se.getSource().getId()));

        Set<Long> existingSrcIds = existing.getSourceEvidences().stream()
                .map(se -> se.getSource().getId())
                .collect(Collectors.toSet());
        for (PersonSourceEvidenceEntity ev : newEvidences) {
            if (!existingSrcIds.contains(ev.getSource().getId())) {
                existing.getSourceEvidences().add(ev);
            }
        }

        return personMapper.toDTO(personRepository.save(existing));
    }

    private LocationEntity safeFetchLocation(Long locationId, String fieldName) {
        return locationId != null ? locationService.fetchLocationById(locationId, fieldName) : null;
    }

    private PersonEntity fetchPersonById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person with ID " + id + " not found"));
    }

    private void validateParentGender(PersonEntity parent, Gender expected, String label) {
        if (parent != null && parent.getGender() != expected) {
            throw new IllegalArgumentException(label + " must be " + expected);
        }
    }

    private List<PersonOccupationEntity> buildOccupationEntities(List<PersonOccupationDTO> dtos, PersonEntity person) {
        if (dtos == null) return new ArrayList<>();
        List<PersonOccupationEntity> list = new ArrayList<>();
        for (PersonOccupationDTO dto : dtos) {
            if (dto.getOccupationId() == null) continue;
            occupationRepository.findById(dto.getOccupationId()).ifPresent(occupation -> {
                PersonOccupationEntity entity = new PersonOccupationEntity();
                entity.setPerson(person);
                entity.setOccupation(occupation);
                entity.setOccupationStartDate(dto.getStartDate());
                entity.setOccupationEndDate(dto.getEndDate());
                list.add(entity);
            });
        }
        return list;
    }

    private List<PersonSourceEvidenceEntity> buildSourceEvidenceEntities(List<PersonSourceEvidenceDTO> dtos, PersonEntity person) {
        if (dtos == null) return new ArrayList<>();
        List<PersonSourceEvidenceEntity> list = new ArrayList<>();
        for (PersonSourceEvidenceDTO dto : dtos) {
            if (dto.getSourceId() == null) continue;
            sourceRepository.findById(dto.getSourceId()).ifPresent(source -> {
                PersonSourceEvidenceEntity entity = new PersonSourceEvidenceEntity();
                entity.setPerson(person);
                entity.setSource(source);
                entity.setPersonName(person.getGivenName() + " " + person.getGivenSurname());
                entity.setSourceName(source.getSourceTitle());
                list.add(entity);
            });
        }
        return list;
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
        List<FamilyEntity> list = new ArrayList<>(getSpousesAsMale(personId));
        list.addAll(getSpousesAsFemale(personId));
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonSourceEvidenceDTO> getSourceEvidences(Long personId) {
        return personSourceEvidenceRepository.findByPersonId(personId)
                .stream()
                .map(e -> {
                    PersonSourceEvidenceDTO dto = personSourceEvidenceMapper.toDTO(e);
                    if (e.getPerson() != null) {
                        dto.setPersonName(e.getPerson().getGivenName() + " " + e.getPerson().getGivenSurname());
                    }
                    if (e.getSource() != null) {
                        dto.setSourceName(e.getSource().getSourceTitle());
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

        Long fatherId = person.getFatherId();
        Long motherId = person.getMotherId();
        if (fatherId == null || motherId == null) return Collections.emptyList();

        return personRepository.findByFatherIdAndMotherId(fatherId, motherId).stream()
                .filter(p -> !p.getId().equals(personId))
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }
}
