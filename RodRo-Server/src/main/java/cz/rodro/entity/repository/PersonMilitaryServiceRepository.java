package cz.rodro.entity.repository;

import cz.rodro.entity.PersonMilitaryServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonMilitaryServiceRepository extends JpaRepository<PersonMilitaryServiceEntity, Long> {

    @Query("SELECT p FROM PersonMilitaryServiceEntity p WHERE p.person.id = :personId")
    List<PersonMilitaryServiceEntity> findByPersonId(@Param("personId") Long personId);

    @Query("SELECT p FROM PersonMilitaryServiceEntity p WHERE LOWER(p.militaryOccupation.rankName) = LOWER(:rank)")
    List<PersonMilitaryServiceEntity> findByRank(@Param("rank") String rank);

    @Query("SELECT p FROM PersonMilitaryServiceEntity p WHERE LOWER(p.militaryOccupation.structure.armyName) LIKE LOWER(CONCAT('%', :army, '%'))")
    List<PersonMilitaryServiceEntity> findByArmyName(@Param("army") String army);

    @Query("SELECT p FROM PersonMilitaryServiceEntity p WHERE p.enlistmentYear BETWEEN :startYear AND :endYear")
    List<PersonMilitaryServiceEntity> findByServicePeriod(@Param("startYear") String startYear, @Param("endYear") String endYear);

    @Query("SELECT p FROM PersonMilitaryServiceEntity p WHERE p.militaryOccupation.structure.country.countryNameInEnglish = :country")
    List<PersonMilitaryServiceEntity> findByCountryName(@Param("country") String country);
}

