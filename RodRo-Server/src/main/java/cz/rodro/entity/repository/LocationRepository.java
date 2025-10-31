package cz.rodro.entity.repository;

import cz.rodro.entity.LocationEntity;
import cz.rodro.dto.LocationListProjection;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing {@link LocationEntity} data.
 * Provides methods for CRUD operations and custom queries related to location entities,
 * optimized for performance and projections.
 */
@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

    /**
     * Fetches a paginated list of locations as projections, allowing optional
     * case-insensitive search by location name.
     *
     * @param searchTerm optional search term to filter location names
     * @param pageable   pagination information
     * @return paginated list of {@link LocationListProjection}
     */
    @Query("""
           SELECT l.id AS id,
                  l.locationName AS locationName,
                  l.establishmentYear AS establishmentYear,
                  l.gpsLatitude AS gpsLatitude,
                  l.gpsLongitude AS gpsLongitude,
                  l.settlementType AS settlementType
           FROM LocationEntity l
           WHERE (:searchTerm IS NULL OR :searchTerm = '' OR 
              LOWER(FUNCTION('REPLACE', 
                FUNCTION('REPLACE', l.locationName, 'ł', 'l'), 'Ł', 'L')
              ) LIKE LOWER(CONCAT('%', :searchTerm, '%')))
       """)
    Page<LocationListProjection> findAllLocationsProjected(
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );

    /**
     * Fetches a {@link LocationEntity} by ID along with its associated
     * location histories and sources to prevent N+1 issues.
     *
     * @param id location ID
     * @return optional {@link LocationEntity} with histories and sources
     */
    @EntityGraph(attributePaths = {"locationHistories", "sources"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<LocationEntity> findWithHistoriesAndSourcesById(Long id);

    /**
     * Finds all locations whose name contains the given string, case-insensitive.
     * Useful for autocomplete or simple searches.
     *
     * @param locationName substring to search in location names
     * @return list of matching {@link LocationEntity}
     */
    List<LocationEntity> findByLocationNameContainingIgnoreCase(String locationName);

    @Query("""
    SELECT l FROM LocationEntity l
    WHERE LOWER(FUNCTION('REPLACE', FUNCTION('REPLACE', l.locationName, 'ł','l'), 'Ł','L')) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
""")
    List<LocationEntity> findByNormalizedLocationName(@Param("searchTerm") String searchTerm);
}
