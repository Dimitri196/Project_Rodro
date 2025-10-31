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
 *
 * <p>Includes custom queries optimized for list views using {@link SourceListProjection}.</p>
 */
@Repository
public interface SourceRepository extends JpaRepository<SourceEntity, Long> {

    /**
     * Finds a paginated and searchable list of sources,
     * returning them as lightweight {@link SourceListProjection}.
     *
     * <p>Only specific fields are selected to reduce payload size
     * and avoid N+1 problems with relationships.</p>
     *
     * @param searchTerm Optional search term for filtering by title, reference, or location name.
     * @param pageable   Pagination and sorting details.
     * @return A page of projections with the requested subset of data.
     */
    @Query(value = """
            SELECT s.id AS id, 
                   s.title AS title, 
                   s.reference AS reference,
                   s.description AS description, 
                   s.url AS url, 
                   s.type AS type,
                   s.creationYear AS creationYear,
                   s.startYear AS startYear,
                     s.endYear AS endYear,
                   l.id AS locationId, 
                   l.locationName AS locationName
            FROM SourceEntity s
            LEFT JOIN s.location l
            WHERE (:searchTerm IS NULL OR :searchTerm = '' OR
                   LOWER(s.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
                   LOWER(s.reference) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
                   LOWER(l.locationName) LIKE LOWER(CONCAT('%', :searchTerm, '%')))
            """,
            countQuery = """
            SELECT COUNT(s.id)
            FROM SourceEntity s
            LEFT JOIN s.location l
            WHERE (:searchTerm IS NULL OR :searchTerm = '' OR
                   LOWER(s.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
                   LOWER(s.reference) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
                   LOWER(l.locationName) LIKE LOWER(CONCAT('%', :searchTerm, '%')))
            """
    )
    Page<SourceListProjection> findAllSourcesProjected(
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );
}
