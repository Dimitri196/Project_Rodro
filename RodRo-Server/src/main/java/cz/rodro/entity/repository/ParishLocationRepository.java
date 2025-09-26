package cz.rodro.entity.repository;

import cz.rodro.entity.ParishLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParishLocationRepository extends JpaRepository<ParishLocationEntity, Long> {

    /** Find all ParishLocation links by Parish ID */
    List<ParishLocationEntity> findByParishId(Long parishId);

    /** Find all ParishLocation links by Location ID */
    List<ParishLocationEntity> findByLocationId(Long locationId);

    /** Find all ParishLocation links by Parish name */
    List<ParishLocationEntity> findByParishName(String name);

    /** Find all ParishLocation links by Location name */
    List<ParishLocationEntity> findByLocationLocationName(String locationName);

    /** Delete a link between a specific Parish and Location */
    void deleteByLocationIdAndParishId(Long locationId, Long parishId);
}
