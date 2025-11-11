package cz.rodro.entity.repository;

import cz.rodro.constant.ConfessionType;
import cz.rodro.constant.SourceType;
import cz.rodro.dto.SourceListProjection;
import cz.rodro.entity.SourceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
/**
 * Repository interface for accessing {@link SourceEntity} data.
 *
 * <p>Includes custom queries optimized for list views using {@link SourceListProjection}.</p>
 */
public interface SourceRepository extends JpaRepository<SourceEntity, Long> {

    @Query(value = """
        SELECT s.id AS id, s.title AS title, s.reference AS reference, s.startYear AS startYear, 
               s.endYear AS endYear, s.creationYear AS creationYear, s.type AS type, s.confession AS confession, 
               s.url AS url, l.id AS locationId, l.locationName AS locationName
        FROM SourceEntity s
        LEFT JOIN s.location l
        WHERE 
            (:titleFilter IS NULL OR 
             LOWER(REPLACE(REPLACE(REPLACE(REPLACE(s.title, 'ą', 'a'), 'ć', 'c'), 'ę', 'e'), 'ł', 'l')) 
             LIKE CONCAT('%', :titleFilter, '%'))
        
        AND 
            (:referenceFilter IS NULL OR 
             LOWER(REPLACE(REPLACE(REPLACE(REPLACE(s.reference, 'ą', 'a'), 'ć', 'c'), 'ę', 'e'), 'ł', 'l')) 
             LIKE CONCAT('%', :referenceFilter, '%'))

        AND 
            (:typeFilter IS NULL OR s.type = :typeFilter)
        
        AND 
            (:confessionFilter IS NULL OR s.confession = :confessionFilter)
            
        AND 
            (:creationYearMinFilter IS NULL OR s.creationYear >= :creationYearMinFilter)

        AND 
            (:locationNameFilter IS NULL OR 
             LOWER(REPLACE(REPLACE(REPLACE(REPLACE(l.locationName, 'ą', 'a'), 'ć', 'c'), 'ę', 'e'), 'ł', 'l')) 
             LIKE CONCAT('%', :locationNameFilter, '%'))
    """, countQuery = """
        SELECT COUNT(s)
        FROM SourceEntity s
        LEFT JOIN s.location l
        WHERE 
            (:titleFilter IS NULL OR LOWER(REPLACE(REPLACE(REPLACE(REPLACE(s.title, 'ą', 'a'), 'ć', 'c'), 'ę', 'e'), 'ł', 'l')) LIKE CONCAT('%', :titleFilter, '%'))
        AND 
            (:referenceFilter IS NULL OR LOWER(REPLACE(REPLACE(REPLACE(REPLACE(s.reference, 'ą', 'a'), 'ć', 'c'), 'ę', 'e'), 'ł', 'l')) LIKE CONCAT('%', :referenceFilter, '%'))
        AND 
            (:typeFilter IS NULL OR s.type = :typeFilter)
        AND 
            (:confessionFilter IS NULL OR s.confession = :confessionFilter)
        AND 
            (:creationYearMinFilter IS NULL OR s.creationYear >= :creationYearMinFilter)
        AND 
            (:locationNameFilter IS NULL OR LOWER(REPLACE(REPLACE(REPLACE(REPLACE(l.locationName, 'ą', 'a'), 'ć', 'c'), 'ę', 'e'), 'ł', 'l')) LIKE CONCAT('%', :locationNameFilter, '%'))
    """)
    Page<SourceListProjection> findAllSourcesProjected(
            @Param("titleFilter") String titleFilter,
            @Param("referenceFilter") String referenceFilter,
            @Param("typeFilter") SourceType typeFilter,
            @Param("confessionFilter") ConfessionType confessionFilter,
            @Param("creationYearMinFilter") Integer creationYearMinFilter,
            @Param("locationNameFilter") String locationNameFilter,
            Pageable pageable
    );
}