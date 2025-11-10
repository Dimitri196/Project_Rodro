package cz.rodro.entity.repository;

import cz.rodro.entity.MilitaryOrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MilitaryOrganizationRepository extends JpaRepository<MilitaryOrganizationEntity, Long> {

    // 1. Find by name (formerly armyName)
    // Uses the new field name 'name' from MilitaryOrganizationEntity
    List<MilitaryOrganizationEntity> findByNameContainingIgnoreCase(String name);

    // 2. Find all organizations active during a given year
    // NOTE: This query uses string comparison, which is necessary since your entity fields are String.
    // However, string comparison for BETWEEN works correctly ONLY if the years are padded (e.g., "0900")
    // or if the underlying database handles lexicographical comparison of the years correctly.
    // If years are simple 4-digit strings (e.g., "1700"), this query should work for basic filtering.
    @Query("SELECT o FROM MilitaryOrganizationEntity o WHERE :year BETWEEN o.activeFromYear AND o.activeToYear")
    List<MilitaryOrganizationEntity> findOrganizationsActiveInYear(@Param("year") String year);

    // 3. Find all organizations for a specific country
    // Uses cleaner JPA naming convention: findBy{Relationship}_{Field}
    List<MilitaryOrganizationEntity> findByCountryId(Long countryId);

    // 4. Find by army branch
    // Uses cleaner JPA naming convention: findBy{Relationship}_{Field}
    List<MilitaryOrganizationEntity> findByArmyBranchId(Long branchId);
}