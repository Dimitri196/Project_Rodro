package cz.rodro.entity.repository;

import cz.rodro.entity.ParishLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParishLocationRepository extends JpaRepository<ParishLocationEntity, Long> {
    List<ParishLocationEntity> findByParishId(Long parishId);

    List<ParishLocationEntity> findByLocationId(Long locationId);

    List<ParishLocationEntity> findByParishParishName(String parishName);

    List<ParishLocationEntity> findByLocationLocationName(String locationName);

    void deleteByLocationIdAndParishId(Long parishId, Long locationId);
}
