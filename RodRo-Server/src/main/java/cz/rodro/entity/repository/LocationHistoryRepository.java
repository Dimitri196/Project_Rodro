package cz.rodro.entity.repository;

import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.LocationHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationHistoryRepository extends JpaRepository<LocationHistoryEntity, Long> {

    List<LocationHistoryEntity> findByLocationId(Long locationId);

    List<LocationHistoryEntity> findByDistrictId(Long districtId);

    List<LocationHistoryEntity> findBySubdivisionId(Long subdivisionId);  // <-- Make sure this method exists

    @Query("SELECT DISTINCT lh.location FROM LocationHistoryEntity lh WHERE lh.subdivision.id = :subdivisionId")
    List<LocationEntity> findDistinctLocationsBySubdivisionId(@Param("subdivisionId") Long subdivisionId);
}
