package cz.rodro.service;

import cz.rodro.dto.MilitaryRankDTO;
import cz.rodro.dto.MilitaryStructureDTO;
import cz.rodro.entity.MilitaryStructureEntity;
import cz.rodro.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Service interface for managing Military Structure entities (e.g., Regiment, Division).
 * Provides standard CRUD operations and methods for accessing associated ranks.
 */
public interface MilitaryStructureService {

    /** Retrieves all military structures. */
    List<MilitaryStructureDTO> getAll();

    /**
     * Retrieves a military structure by ID.
     * @param structureId The ID of the structure.
     * @return The corresponding MilitaryStructureDTO.
     * @throws ResourceNotFoundException if the structure is not found.
     */
    MilitaryStructureDTO getMilitaryStructure(Long structureId);

    /**
     * Creates a new military structure, linking it to a Military Organization.
     * @param militaryStructureDTO The DTO containing the structure data.
     * @return The created MilitaryStructureDTO.
     */
    MilitaryStructureDTO addMilitaryStructure(MilitaryStructureDTO militaryStructureDTO);

    /**
     * Deletes a military structure by its ID.
     * @param structureId The ID of the structure to delete.
     * @throws ResourceNotFoundException if the structure is not found.
     */
    void deleteMilitaryStructure(Long structureId); // Changed return type to void for standard CRUD

    /**
     * Updates an existing military structure.
     * @param structureId The ID of the structure to update.
     * @param militaryStructureDTO The DTO containing the updated data.
     * @return The updated MilitaryStructureDTO.
     * @throws ResourceNotFoundException if the structure is not found.
     */
    MilitaryStructureDTO updateMilitaryStructure(Long structureId, MilitaryStructureDTO militaryStructureDTO);

    /**
     * Fetches the raw MilitaryStructureEntity. Used internally or for cross-service calls.
     * @param id The ID of the structure.
     * @param type The type string for use in exception messages.
     * @return The raw MilitaryStructureEntity.
     * @throws ResourceNotFoundException if the structure is not found.
     */
    MilitaryStructureEntity fetchMilitaryStructureById(Long id, String type);

    /**
     * Retrieves all ranks directly associated with a specific military structure.
     * @param structureId The ID of the military structure.
     * @return A list of MilitaryRankDTOs.
     */
    List<MilitaryRankDTO> getRanksForStructure(Long structureId);

}
