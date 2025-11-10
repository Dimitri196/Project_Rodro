package cz.rodro.service;

import cz.rodro.dto.CountryDTO;
import cz.rodro.entity.CountryEntity;
import cz.rodro.exception.ResourceNotFoundException;

import java.util.List;

/**
 * Defines the business contract for Country management operations.
 * This interface abstracts the underlying persistence and provides core CRUD functionality.
 */
public interface CountryService {

    /**
     * Retrieves all Country records.
     * @return A list of all countries as DTOs.
     */
    List<CountryDTO> getAll();

    /**
     * Retrieves a single Country by its unique identifier, including its associated provinces.
     * @param countryId The ID of the country to retrieve.
     * @return The CountryDTO containing the country details and province list.
     * @throws ResourceNotFoundException if no country exists with the given ID.
     */
    CountryDTO getCountry(long countryId);

    /**
     * Creates and persists a new Country record.
     * @param countryDTO The DTO containing data for the new country.
     * @return The saved CountryDTO, including the generated ID.
     */
    CountryDTO addCountry(CountryDTO countryDTO);

    /**
     * Updates an existing Country record identified by its ID.
     * @param countryId The ID of the country to update.
     * @param countryDTO The DTO containing the updated data.
     * @return The updated CountryDTO.
     * @throws ResourceNotFoundException if no country exists with the given ID.
     */
    CountryDTO updateCountry(Long countryId, CountryDTO countryDTO);

    /**
     * Helper method to fetch a CountryEntity by ID. Used internally by other services
     * or for relations that require the Entity object.
     *
     * @param id The ID of the CountryEntity to fetch.
     * @param type A descriptive string (e.g., "Country") to include in the exception message.
     * @return The requested CountryEntity.
     * @throws ResourceNotFoundException if the entity is not found.
     */
    CountryEntity fetchCountryById(Long id, String type);

}
