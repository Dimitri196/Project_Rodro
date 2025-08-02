package cz.rodro.service;

import cz.rodro.dto.LocationDTO;
import cz.rodro.dto.LocationListProjection;
import cz.rodro.entity.LocationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing {@link LocationEntity} and {@link LocationDTO} operations.
 * Provides methods for CRUD operations, with an emphasis on optimized data retrieval
 * for lists and detailed views, and proper handling of nested relationships.
 */
public interface LocationService {

    /**
     * Retrieves a paginated and searchable list of locations,
     * returning them as a lightweight LocationListProjection for optimized performance.
     *
     * @param searchTerm An optional term to filter by location name.
     * @param pageable   Pagination and sorting information.
     * @return A Page of LocationListProjection.
     */
    Page<LocationListProjection> getAllLocations(String searchTerm, Pageable pageable);

    /**
     * Retrieves a single location by its ID, including its associated historical records and sources.
     *
     * @param locationId The ID of the location to retrieve.
     * @return The LocationDTO with detailed information.
     * @throws cz.rodro.exception.NotFoundException if the location is not found.
     */
    LocationDTO getLocation(long locationId);

    /**
     * Adds a new location to the database.
     *
     * @param locationDTO The LocationDTO containing the data for the new location.
     * @return The created LocationDTO.
     */
    LocationDTO addLocation(LocationDTO locationDTO);

    /**
     * Removes a location from the database by its ID.
     *
     * @param locationId The ID of the location to remove.
     * @throws cz.rodro.exception.NotFoundException if the location is not found.
     */
    void removeLocation(long locationId);

    /**
     * Updates an existing location with new data. Handles updates to basic fields
     * and manages associated collections (location histories, sources).
     *
     * @param locationId The ID of the location to update.
     * @param locationDTO The LocationDTO containing the updated data.
     * @return The updated LocationDTO.
     * @throws cz.rodro.exception.NotFoundException if the location is not found.
     */
    LocationDTO updateLocation(Long locationId, LocationDTO locationDTO);

    /**
     * Fetches a LocationEntity by its ID. This is a helper method primarily for internal
     * service use when other entities need to link to a Location.
     *
     * @param id The ID of the location to fetch.
     * @param type A string describing the type of location (e.g., "Birth Place") for error messages.
     * @return The LocationEntity.
     * @throws cz.rodro.exception.NotFoundException if the location is not found.
     */
    LocationEntity fetchLocationById(Long id, String type);
}
