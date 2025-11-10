package cz.rodro.service;

import cz.rodro.dto.MilitaryRankDTO;
import cz.rodro.entity.MilitaryRankEntity;
import cz.rodro.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Service interface for managing Military Rank entities (e.g., Major, General).
 * Provides standard CRUD operations and a specialized method for fetching associated personnel.
 */
public interface MilitaryRankService {

    /** Retrieves all military ranks. */
    List<MilitaryRankDTO> getAll();

    /**
     * Retrieves a military rank by ID.
     * @param rankId The ID of the rank.
     * @return The corresponding MilitaryRankDTO.
     * @throws ResourceNotFoundException if the rank is not found.
     */
    MilitaryRankDTO getMilitaryRank(Long rankId);

    /**
     * Creates a new military rank, linking it to an Organization and an optional Structure.
     * @param militaryRankDTO The DTO containing the rank data.
     * @return The created MilitaryRankDTO.
     */
    MilitaryRankDTO addMilitaryRank(MilitaryRankDTO militaryRankDTO);

    /**
     * Deletes a military rank by its ID.
     * @param rankId The ID of the rank to delete.
     * @throws ResourceNotFoundException if the rank is not found.
     */
    void deleteMilitaryRank(Long rankId); // Changed return type to void

    /**
     * Updates an existing military rank.
     * @param rankId The ID of the rank to update.
     * @param militaryRankDTO The DTO containing the updated data.
     * @return The updated MilitaryRankDTO.
     * @throws ResourceNotFoundException if the rank is not found.
     */
    MilitaryRankDTO updateMilitaryRank(Long rankId, MilitaryRankDTO militaryRankDTO);

    /**
     * Fetches the raw MilitaryRankEntity. Used internally or for cross-service calls.
     * @param id The ID of the rank.
     * @param type The type string for use in exception messages.
     * @return The raw MilitaryRankEntity.
     * @throws ResourceNotFoundException if the rank is not found.
     */
    MilitaryRankEntity fetchMilitaryRankById(Long id, String type);

    /**
     * Retrieves a military rank by ID and populates the list of persons holding that rank.
     * @param id The ID of the rank.
     * @return The MilitaryRankDTO with the 'persons' list populated.
     * @throws ResourceNotFoundException if the rank is not found.
     */
    MilitaryRankDTO getMilitaryRankWithPersons(Long id);
}
