package cz.rodro.entity.repository;

import cz.rodro.entity.MilitaryOrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MilitaryOrganizationRepository extends JpaRepository<MilitaryOrganizationEntity, Long> {

    // Find by army name
    List<MilitaryOrganizationEntity> findByArmyNameContainingIgnoreCase(String armyName);

    // Find all organizations active during a given year
    @Query("SELECT o FROM MilitaryOrganizationEntity o WHERE :year BETWEEN o.activeFromYear AND o.activeToYear")
    List<MilitaryOrganizationEntity> findOrganizationsActiveInYear(@Param("year") String year);

    // Find all organizations for a specific country
    List<MilitaryOrganizationEntity> findByCountry_Id(Long countryId);

}