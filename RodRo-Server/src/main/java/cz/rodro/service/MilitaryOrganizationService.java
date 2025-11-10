package cz.rodro.service;

import cz.rodro.dto.MilitaryOrganizationDTO;
import cz.rodro.entity.MilitaryOrganizationEntity;
import cz.rodro.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Service interface for managing Military Organization entities (e.g., Prussian Army).
 * Provides standard CRUD operations and a method to fetch the raw entity.
 */
public interface MilitaryOrganizationService {

    /** Retrieves all military organizations. */
    List<MilitaryOrganizationDTO> getAll();

    /**
     * Retrieves a military organization by ID.
     * @param organizationId The ID of the organization.
     * @return The corresponding MilitaryOrganizationDTO.
     * @throws ResourceNotFoundException if the organization is not found.
     */
    MilitaryOrganizationDTO getMilitaryOrganization(Long organizationId);

    /**
     * Creates a new military organization, linking it to Country and Army Branch IDs.
     * @param militaryOrganizationDTO The DTO containing the organization data.
     * @return The created MilitaryOrganizationDTO.
     */
    MilitaryOrganizationDTO addMilitaryOrganization(MilitaryOrganizationDTO militaryOrganizationDTO);

    /**
     * Deletes a military organization by its ID.
     * @param organizationId The ID of the organization to delete.
     * @throws ResourceNotFoundException if the organization is not found.
     */
    void deleteMilitaryOrganization(Long organizationId);

    /**
     * Updates an existing military organization.
     * @param organizationId The ID of the organization to update.
     * @param militaryOrganizationDTO The DTO containing the updated data.
     * @return The updated MilitaryOrganizationDTO.
     * @throws ResourceNotFoundException if the organization is not found.
     */
    MilitaryOrganizationDTO updateMilitaryOrganization(Long organizationId, MilitaryOrganizationDTO militaryOrganizationDTO);

    /**
     * Fetches the raw MilitaryOrganizationEntity. Used internally or for cross-service calls.
     * @param id The ID of the organization.
     * @param type The type string for use in exception messages.
     * @return The raw MilitaryOrganizationEntity.
     * @throws ResourceNotFoundException if the organization is not found.
     */
    MilitaryOrganizationEntity fetchMilitaryOrganizationById(Long id, String type);

}
