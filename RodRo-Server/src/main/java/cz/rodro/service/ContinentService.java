package cz.rodro.service;

import cz.rodro.dto.ContinentDTO;

import java.util.List;

/**
 * Interface for business logic related to Continent entities.
 * Handles CRUD operations for the static continent lookup data.
 */
public interface ContinentService {

    /**
     * Retrieves a list of all continents.
     * @return A list of ContinentDTOs.
     */
    List<ContinentDTO> getAllContinents();

    /**
     * Retrieves a single continent by its unique ID.
     * @param continentId The ID of the continent.
     * @return The ContinentDTO.
     */
    ContinentDTO getContinent(Long continentId);

    /**
     * Creates a new continent record.
     * @param continentDTO The DTO containing the new continent data.
     * @return The newly created ContinentDTO.
     */
    ContinentDTO addContinent(ContinentDTO continentDTO);

    /**
     * Updates an existing continent record.
     * @param continentId The ID of the continent to update.
     * @param continentDTO The DTO containing the updated data.
     * @return The updated ContinentDTO.
     */
    ContinentDTO updateContinent(Long continentId, ContinentDTO continentDTO);

    /**
     * Deletes a continent record by ID.
     * @param continentId The ID of the continent to delete.
     */
    void deleteContinent(Long continentId);
}
