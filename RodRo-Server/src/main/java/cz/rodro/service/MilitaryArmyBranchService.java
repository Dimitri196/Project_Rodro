package cz.rodro.service;

import cz.rodro.dto.MilitaryArmyBranchDTO;
import cz.rodro.dto.MilitaryRankDTO;

import java.util.List;

/**
 * Service interface for managing Military Army Branch entities (e.g., Infantry, Cavalry).
 * Provides standard CRUD operations and custom lookup methods for related Ranks.
 */
public interface MilitaryArmyBranchService {

    /**
     * Retrieves all military army branches.
     *
     * @return A list of all MilitaryArmyBranchDTOs.
     */
    List<MilitaryArmyBranchDTO> findAll();

    /**
     * Retrieves a military army branch by its unique identifier.
     *
     * @param id The ID of the army branch.
     * @return The corresponding MilitaryArmyBranchDTO.
     * @throws jakarta.persistence.EntityNotFoundException if the branch is not found.
     */
    MilitaryArmyBranchDTO findById(Long id);

    /**
     * Creates a new military army branch.
     *
     * @param dto The MilitaryArmyBranchDTO containing the data for the new branch.
     * @return The created MilitaryArmyBranchDTO.
     */
    MilitaryArmyBranchDTO create(MilitaryArmyBranchDTO dto);

    /**
     * Updates an existing military army branch.
     *
     * @param id The ID of the branch to update.
     * @param dto The MilitaryArmyBranchDTO containing the updated data.
     * @return The updated MilitaryArmyBranchDTO.
     * @throws jakarta.persistence.EntityNotFoundException if the branch is not found.
     */
    MilitaryArmyBranchDTO update(Long id, MilitaryArmyBranchDTO dto);

    /**
     * Deletes a military army branch by its unique identifier.
     *
     * @param id The ID of the branch to delete.
     * @throws jakarta.persistence.EntityNotFoundException if the branch is not found.
     */
    void delete(Long id);

    /**
     * Retrieves all ranks associated with organizations belonging to a specific army branch.
     *
     * @param branchId The ID of the Military Army Branch.
     * @return A list of MilitaryRankDTOs.
     */
    List<MilitaryRankDTO> getRanksByBranchId(Long branchId);
}
