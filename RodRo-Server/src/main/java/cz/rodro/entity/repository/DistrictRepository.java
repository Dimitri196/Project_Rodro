package cz.rodro.entity.repository;

import cz.rodro.entity.DistrictEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictRepository extends JpaRepository<DistrictEntity, Long> {

    // Fetch districts by province
    List<DistrictEntity> findByProvinceId(Long provinceId);

}
