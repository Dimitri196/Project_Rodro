package cz.rodro.entity.repository;

import cz.rodro.dto.LocationListProjection;
import cz.rodro.entity.LocationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for accessing {@link LocationEntity} data.
 * Provides methods for CRUD operations and custom queries related to location entities,
 * with an emphasis on performance optimization for related collections.
 */
@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

    @Query(value = "SELECT l.id AS id, l.locationName AS locationName, l.establishmentYear AS establishmentYear, " +
            "l.gpsLatitude AS gpsLatitude, l.gpsLongitude AS gpsLongitude, l.settlementType AS settlementType " +
            "FROM location l " + // 'location' refers to the entity name, not necessarily the table name
            "WHERE (:searchTerm IS NULL OR :searchTerm = '' OR " +
            "       LOWER(l.locationName) LIKE LOWER(CONCAT('%', :searchTerm, '%')))",
            countQuery = "SELECT COUNT(l.id) FROM location l " +
                    "WHERE (:searchTerm IS NULL OR :searchTerm = '' OR " +
                    "       LOWER(l.locationName) LIKE LOWER(CONCAT('%', :searchTerm, '%')))"
    )
    Page<LocationListProjection> findAllLocationsProjected(
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );

    @EntityGraph(attributePaths = {"locationHistories", "sources"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<LocationEntity> findById(Long id);
}
