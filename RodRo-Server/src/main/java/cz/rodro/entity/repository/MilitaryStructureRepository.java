package cz.rodro.entity.repository;

import cz.rodro.entity.MilitaryStructureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MilitaryStructureRepository extends JpaRepository<MilitaryStructureEntity, Long> {

    @Query("SELECT m FROM MilitaryStructureEntity m WHERE LOWER(m.armyName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<MilitaryStructureEntity> searchByArmyName(@Param("name") String name);

    @Query("SELECT m FROM MilitaryStructureEntity m WHERE m.country.countryNameInEnglish = :country")
    List<MilitaryStructureEntity> findByCountryName(@Param("country") String country);

    @Query("SELECT m FROM MilitaryStructureEntity m WHERE LOWER(m.unitType) = LOWER(:type)")
    List<MilitaryStructureEntity> findByUnitType(@Param("type") String type);
}

