package cz.rodro.entity.repository;

import cz.rodro.dto.SourceListProjection;
import cz.rodro.entity.SourceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing {@link SourceEntity} data.
 * Provides methods for CRUD operations and custom queries related to source entities,
 * with an emphasis on performance optimization for related collections.
 */
@Repository
public interface SourceRepository extends JpaRepository<SourceEntity, Long> {

    /**
     * Finds a paginated and searchable list of sources,
     * returning them as a lightweight SourceListProjection.
     * This query selects specific fields to avoid N+1 problems and
     * reduce the data payload for list views.
     *
     * @param searchTerm An optional term to filter by source title, reference, or location name.
     * If null or empty, no text filtering is applied.
     * @param pageable   Pagination and sorting information (page number, size, sort order).
     * @return A Page of SourceListProjection containing the requested subset of data.
     */
    @Query(value = "SELECT s.id AS id, s.sourceTitle AS sourceTitle, s.sourceReference AS sourceReference, " +
            "s.sourceDescription AS sourceDescription, s.sourceUrl AS sourceUrl, s.sourceType AS sourceType, " +
            "sl.id AS sourceLocationId, sl.locationName AS sourceLocationName " +
            "FROM SourceEntity s " +
            "LEFT JOIN s.sourceLocation sl " +
            "WHERE (:searchTerm IS NULL OR :searchTerm = '' OR " +
            "       LOWER(s.sourceTitle) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "       LOWER(s.sourceReference) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            // Removed: LOWER(s.sourceDescription) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
            "       LOWER(sl.locationName) LIKE LOWER(CONCAT('%', :searchTerm, '%')))",
            countQuery = "SELECT COUNT(s.id) FROM SourceEntity s " +
                    "LEFT JOIN s.sourceLocation sl " +
                    "WHERE (:searchTerm IS NULL OR :searchTerm = '' OR " +
                    "       LOWER(s.sourceTitle) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
                    "       LOWER(s.sourceReference) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
                    // Removed: LOWER(s.sourceDescription) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
                    "       LOWER(sl.locationName) LIKE LOWER(CONCAT('%', :searchTerm, '%')))"
    )
    Page<SourceListProjection> findAllSourcesProjected(
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );
}
