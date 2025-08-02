package cz.rodro.service;

import cz.rodro.dto.SourceDTO;
import cz.rodro.dto.SourceListProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for managing {@link SourceDTO} operations.
 * Provides methods for CRUD operations and optimized data retrieval for sources.
 */
public interface SourceService {

    /**
     * Retrieves a paginated and searchable list of sources.
     *
     * @param searchTerm An optional term to filter sources.
     * @param pageable   Pagination and sorting information.
     * @return A Page of SourceListProjection (or SourceDTOs if no projection).
     */
    Page<SourceListProjection> getAllSources(String searchTerm, Pageable pageable); // Assuming SourceListProjection

    /**
     * Retrieves a single source by its ID.
     *
     * @param sourceId The ID of the source to retrieve.
     * @return The SourceDTO with detailed information.
     * @throws cz.rodro.exception.NotFoundException if the source is not found.
     */
    SourceDTO getSource(long sourceId);

    /**
     * Adds a new source to the database.
     *
     * @param sourceDTO The SourceDTO containing the data for the new source.
     * @return The created SourceDTO.
     */
    SourceDTO addSource(SourceDTO sourceDTO);

    /**
     * Removes a source from the database by its ID.
     *
     * @param sourceId The ID of the source to remove.
     * @throws cz.rodro.exception.NotFoundException if the source is not found.
     */
    void removeSource(long sourceId);

    /**
     * Updates an existing source with new data.
     *
     * @param sourceId The ID of the source to update.
     * @param sourceDTO The SourceDTO containing the updated data.
     * @return The updated SourceDTO.
     * @throws cz.rodro.exception.NotFoundException if the source is not found.
     */
    SourceDTO updateSource(Long sourceId, SourceDTO sourceDTO);
}
