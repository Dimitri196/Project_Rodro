package cz.rodro.service;

import cz.rodro.dto.CountryContinentHistoryDTO;

import java.util.List;

/**
 * Interface for business logic managing the temporal association between countries and continents.
 */
public interface CountryContinentHistoryService {

    /**
     * Retrieves all history records for a specific country, ordered chronologically.
     * @param countryId The ID of the country.
     * @return A list of CountryContinentHistoryDTOs.
     */
    List<CountryContinentHistoryDTO> getHistoryByCountry(Long countryId);

    /**
     * Adds a new temporal association record (startYear, endYear) between a country and a continent.
     * @param dto The DTO containing the countryId, continentId, and temporal data.
     * @return The newly created history DTO.
     */
    CountryContinentHistoryDTO addHistoryRecord(CountryContinentHistoryDTO dto);

    /**
     * Updates an existing history record, typically modifying the start/end years.
     * @param id The ID of the history record to update.
     * @param dto The DTO containing updated temporal data.
     * @return The updated history DTO.
     */
    CountryContinentHistoryDTO updateHistoryRecord(Long id, CountryContinentHistoryDTO dto);

    /**
     * Deletes a specific history record by ID.
     * @param id The ID of the history record to delete.
     */
    void deleteHistoryRecord(Long id);
}
