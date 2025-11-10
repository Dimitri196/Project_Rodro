package cz.rodro.service;

import cz.rodro.dto.PersonMilitaryServiceDTO;
import cz.rodro.entity.PersonMilitaryServiceEntity;
import cz.rodro.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Service interface for managing Person Military Service records.
 * Provides standard CRUD operations and custom lookups by Person ID.
 */
public interface PersonMilitaryService {

    /** Retrieves all military service records. */
    List<PersonMilitaryServiceDTO> getAll();

    /**
     * Retrieves a military service record by its unique ID.
     * @param serviceId The ID of the service record.
     * @return The corresponding PersonMilitaryServiceDTO.
     * @throws ResourceNotFoundException if the record is not found.
     */
    PersonMilitaryServiceDTO getPersonMilitaryService(Long serviceId);

    /**
     * Creates a new military service record, linking it to a Person, Structure, and Rank.
     * @param dto The DTO containing the service data.
     * @return The created PersonMilitaryServiceDTO.
     */
    PersonMilitaryServiceDTO addPersonMilitaryService(PersonMilitaryServiceDTO dto);

    /**
     * Deletes a military service record by its ID.
     * @param serviceId The ID of the service record to delete.
     * @throws ResourceNotFoundException if the record is not found.
     */
    void removePersonMilitaryService(Long serviceId);

    /**
     * Updates an existing military service record.
     * @param serviceId The ID of the record to update.
     * @param dto The DTO containing the updated data.
     * @return The updated PersonMilitaryServiceDTO.
     * @throws ResourceNotFoundException if the record is not found.
     */
    PersonMilitaryServiceDTO updatePersonMilitaryService(Long serviceId, PersonMilitaryServiceDTO dto);

    /**
     * Fetches the raw PersonMilitaryServiceEntity. Used internally or for cross-service calls.
     * @param id The ID of the service record.
     * @param type The type string for use in exception messages.
     * @return The raw PersonMilitaryServiceEntity.
     * @throws ResourceNotFoundException if the record is not found.
     */
    PersonMilitaryServiceEntity fetchPersonMilitaryServiceById(Long id, String type);

    /**
     * Retrieves all military service records associated with a specific person ID.
     * @param personId The ID of the Person.
     * @return A list of PersonMilitaryServiceDTOs.
     */
    List<PersonMilitaryServiceDTO> getByPersonId(Long personId);

}
