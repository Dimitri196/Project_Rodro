package cz.rodro.service;

import cz.rodro.constant.Gender;
import cz.rodro.dto.FamilyDTO;
import cz.rodro.dto.PersonDTO;
import cz.rodro.dto.mapper.FamilyMapper;
import cz.rodro.entity.FamilyEntity;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.PersonEntity;
import cz.rodro.entity.filter.FamilyFilter;
import cz.rodro.entity.repository.FamilyRepository;
import cz.rodro.entity.repository.LocationRepository;
import cz.rodro.entity.repository.PersonRepository;
import cz.rodro.entity.repository.specification.FamilySpecification;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import cz.rodro.exception.NotFoundException;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FamilyServiceImpl implements FamilyService {

    private static final Logger log = LoggerFactory.getLogger(FamilyServiceImpl.class);

    @Autowired
    private FamilyMapper familyMapper;

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private LocationRepository locationRepository;

    /**
     * Adds a new family to the system, associating members and witnesses based on provided IDs.
     *
     * @param familyDTO The data transfer object containing the family details to be added.
     * @return The added family as a {@link FamilyDTO}.
     * @throws NotFoundException if any related entities are not found.
     */
    @Override
    @Transactional
    public FamilyDTO addFamily(FamilyDTO familyDTO) {
        // Fetch spouseMale and spouseFemale entities
        PersonEntity spouseMale = fetchPersonEntity(familyDTO.getSpouseMale().getId(), "Spouse Male");
        PersonEntity spouseFemale = fetchPersonEntity(familyDTO.getSpouseFemale().getId(), "Spouse Female");

        // Validate genders
        validateGender(spouseMale, Gender.MALE, "Spouse Male");
        validateGender(spouseFemale, Gender.FEMALE, "Spouse Female");

        // Fetch witnesses if they are provided
        PersonEntity witnessMaleSide1 = fetchOptionalPerson(familyDTO.getWitnessesMaleSide1());
        PersonEntity witnessMaleSide2 = fetchOptionalPerson(familyDTO.getWitnessesMaleSide2());
        PersonEntity witnessFemaleSide1 = fetchOptionalPerson(familyDTO.getWitnessesFemaleSide1());
        PersonEntity witnessFemaleSide2 = fetchOptionalPerson(familyDTO.getWitnessesFemaleSide2());

        // Fetch marriage location
        LocationEntity marriageLocation = locationRepository.findById(familyDTO.getMarriageLocation().getId())
                .orElseThrow(() -> new NotFoundException("Marriage location with id " + familyDTO.getMarriageLocation().getId() + " not found."));

        // Map DTO to entity and set relationships
        FamilyEntity familyEntity = familyMapper.toFamilyEntity(familyDTO);
        familyEntity.setSpouseMale(spouseMale);
        familyEntity.setSpouseFemale(spouseFemale);
        familyEntity.setWitnessesMaleSide1(witnessMaleSide1);
        familyEntity.setWitnessesMaleSide2(witnessMaleSide2);
        familyEntity.setWitnessesFemaleSide1(witnessFemaleSide1);
        familyEntity.setWitnessesFemaleSide2(witnessFemaleSide2);
        familyEntity.setMarriageLocation(marriageLocation);

        familyEntity = familyRepository.save(familyEntity);

        return familyMapper.toFamilyDTO(familyEntity);
    }

    /**
     * Retrieves a family by its ID.
     *
     * @param familyId The ID of the family to retrieve.
     * @return The corresponding family as a {@link FamilyDTO}.
     */
    @Override
    public FamilyDTO getFamily(Long familyId) {
        FamilyEntity familyEntity = fetchFamilyById(familyId);
        return familyMapper.toFamilyDTO(familyEntity);
    }

    /**
     * Updates an existing family with the provided data.
     *
     * @param familyId  The ID of the family to update.
     * @param familyDTO The new data for the family.
     * @return The updated family as a {@link FamilyDTO}.
     * @throws NotFoundException if any related entities are not found.
     */
    @Override
    @Transactional
    public FamilyDTO updateFamily(Long familyId, FamilyDTO familyDTO) {
        FamilyEntity existingFamily = fetchFamilyById(familyId);

        // Fetch the new entities for spouseMale and spouseFemale by their IDs
        PersonEntity spouseMale = fetchPersonEntity(familyDTO.getSpouseMale().getId(), "Spouse Male");
        PersonEntity spouseFemale = fetchPersonEntity(familyDTO.getSpouseFemale().getId(), "Spouse Female");

        // Validate genders
        validateGender(spouseMale, Gender.MALE, "Spouse Male");
        validateGender(spouseFemale, Gender.FEMALE, "Spouse Female");

        // Fetch the optional witnesses
        PersonEntity witnessMaleSide1 = fetchOptionalPerson(familyDTO.getWitnessesMaleSide1());
        PersonEntity witnessMaleSide2 = fetchOptionalPerson(familyDTO.getWitnessesMaleSide2());
        PersonEntity witnessFemaleSide1 = fetchOptionalPerson(familyDTO.getWitnessesFemaleSide1());
        PersonEntity witnessFemaleSide2 = fetchOptionalPerson(familyDTO.getWitnessesFemaleSide2());

        // Fetch the marriage location by ID
        LocationEntity marriageLocation = locationRepository.findById(familyDTO.getMarriageLocation().getId())
                .orElseThrow(() -> new NotFoundException("Marriage location with id " + familyDTO.getMarriageLocation().getId() + " not found."));

        // Use the mapper to update the existing entity (without overwriting relationships)
        familyMapper.updateFamilyEntity(familyDTO, existingFamily);

        // Set the new relationships without changing the IDs

        existingFamily.setSpouseMale(spouseMale);
        existingFamily.setSpouseFemale(spouseFemale);
        existingFamily.setWitnessesMaleSide1(witnessMaleSide1);
        existingFamily.setWitnessesMaleSide2(witnessMaleSide2);
        existingFamily.setWitnessesFemaleSide1(witnessFemaleSide1);
        existingFamily.setWitnessesFemaleSide2(witnessFemaleSide2);
        existingFamily.setMarriageLocation(marriageLocation);

        // Save the updated entity
        FamilyEntity updatedFamily = familyRepository.save(existingFamily);

        // Convert the updated entity back to a DTO
        return familyMapper.toFamilyDTO(updatedFamily);
    }

    /**
     * Removes a family by its ID.
     *
     * @param familyId The ID of the family to be removed.
     */
    @Override
    @Transactional
    public void removeFamily(Long familyId) {
        FamilyEntity family = fetchFamilyById(familyId);
        familyRepository.delete(family);
        log.info("Family with id {} has been deleted successfully.", familyId);
    }

    // Helper methods

    private FamilyEntity fetchFamilyById(Long familyId) {
        return familyRepository.findById(familyId)
                .orElseThrow(() -> new NotFoundException("Family with id " + familyId + " wasn't found in the database."));
    }

    private PersonEntity fetchPersonEntity(Long personId, String role) {
        return personRepository.findById(personId)
                .orElseThrow(() -> new NotFoundException(role + " with id " + personId + " not found."));
    }

    private PersonEntity fetchOptionalPerson(PersonDTO personDTO) {
        return personDTO != null ? fetchPersonEntity(personDTO.getId(), "Witness") : null;
    }


    public List<FamilyDTO> getAllFamilies(FamilyFilter familyFilter) {
        FamilySpecification familySpecification = new FamilySpecification(familyFilter);

        return familyRepository.findAll(familySpecification, PageRequest.of(0, familyFilter.getLimit()))
                .stream()
                .map(familyMapper::toFamilyDTO)
                .collect(Collectors.toList());
    }

    private void validateGender(PersonEntity person, Gender expectedGender, String role) {
        if (person.getGender() != expectedGender) {
            throw new IllegalArgumentException(role + " must have gender " + expectedGender + ".");
        }
    }

    @Override
    public List<FamilyDTO> getAllFamilies() {
        return familyRepository.findAll().stream()
                .map(familyMapper::toFamilyDTO)
                .collect(Collectors.toList());
    }

    public List<FamilyDTO> findFamiliesByChild(Long childId) {
        // Assuming FamilyRepository can find families by child
        List<FamilyEntity> families = familyRepository.findByChildrenId(childId);
        return families.stream()
                .map(familyMapper::toFamilyDTO)
                .collect(Collectors.toList());
    }

    public List<FamilyDTO> getSpouses(Long personId) {
        List<FamilyEntity> families = familyRepository.findBySpouseMaleIdOrSpouseFemaleId(personId, personId);
        return families.stream()
                .map(familyMapper::toFamilyDTO)
                .collect(Collectors.toList());
    }
}
