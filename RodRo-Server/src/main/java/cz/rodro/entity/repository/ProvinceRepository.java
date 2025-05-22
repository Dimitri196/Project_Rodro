package cz.rodro.entity.repository;

import cz.rodro.entity.ProvinceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProvinceRepository extends JpaRepository<ProvinceEntity, Long> {

    List<ProvinceEntity> findByCountryId(Long countryId);
}
