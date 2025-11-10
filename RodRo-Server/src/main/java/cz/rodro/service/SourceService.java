package cz.rodro.service;

import cz.rodro.dto.SourceDTO;
import cz.rodro.dto.SourceListDTO;
import cz.rodro.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing {@link cz.rodro.entity.SourceEntity} operations.
 * Provides methods for CRUD operations and optimized data retrieval for sources.
 */
public interface SourceService {

    /**
     * Retrieves a paginated and searchable list of sources as frontend-ready DTOs.
     *
     * @param searchTerm An optional term to filter sources by title, reference, or location name.
     * @param pageable   Pagination and sorting information.
     * @return A Page of {@link SourceListDTO} containing the requested subset of data.
     */
    Page<SourceListDTO> getAllSourcesAsDTO(String searchTerm, Pageable pageable);

    /**
     * Retrieves a single source by its ID.
     *
     * @param sourceId The ID of the source to retrieve.
     * @return The {@link SourceDTO} with detailed information.
     * @throws ResourceNotFoundException if the source is not found.
     */
    SourceDTO getSource(Long sourceId);

    /**
     * Adds a new source to the database.
     *
     * @param sourceDTO The {@link SourceDTO} containing the data for the new source.
     * @return The created {@link SourceDTO}.
     */
    SourceDTO addSource(SourceDTO sourceDTO);

    /**
     * Removes a source from the database by its ID.
     *
     * @param sourceId The ID of the source to remove.
     * @throws ResourceNotFoundException if the source is not found.
     */
    void removeSource(Long sourceId);

    /**
     * Updates an existing source with new data.
     *
     * @param sourceId  The ID of the source to update.
     * @param sourceDTO The {@link SourceDTO} containing the updated data.
     * @return The updated {@link SourceDTO}.
     * @throws ResourceNotFoundException if the source is not found.
     */
    SourceDTO updateSource(Long sourceId, SourceDTO sourceDTO);
}