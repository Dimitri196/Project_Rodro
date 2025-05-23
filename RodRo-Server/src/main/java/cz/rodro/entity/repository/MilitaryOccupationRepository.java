package cz.rodro.entity.repository;

import cz.rodro.entity.MilitaryOccupationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MilitaryOccupationRepository extends JpaRepository<MilitaryOccupationEntity, Long> {

    @Query("SELECT mo FROM MilitaryOccupationEntity mo WHERE LOWER(mo.rankName) = LOWER(:rank)")
    List<MilitaryOccupationEntity> findByRankName(@Param("rank") String rank);

    @Query("SELECT mo FROM MilitaryOccupationEntity mo WHERE mo.structure.id = :structureId")
    List<MilitaryOccupationEntity> findByStructureId(@Param("structureId") Long structureId);

    @Query("SELECT mo FROM MilitaryOccupationEntity mo WHERE LOWER(mo.structure.armyName) LIKE LOWER(CONCAT('%', :army, '%'))")
    List<MilitaryOccupationEntity> findByArmyName(@Param("army") String army);
}

