package cz.rodro.entity.repository;

import cz.rodro.constant.MilitaryRankLevel;
import cz.rodro.entity.MilitaryRankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MilitaryRankRepository extends JpaRepository<MilitaryRankEntity, Long> {

    // 1. Find by Rank Level (Corrected to use the Enum type)
    /**
     * Finds ranks by their categorical level (e.g., OFFICER, ENLISTED).
     */
    List<MilitaryRankEntity> findByRankLevel(MilitaryRankLevel rankLevel);

    // 2. Find by ID and Eagerly Fetch Personnel
    // NOTE: Changed return type to Optional<MilitaryRankEntity> for safety, and ensures only one result.
    // Also, use JOIN FETCH for collection loading.
    /**
     * Finds a rank by ID and eagerly fetches its associated personnel records.
     * Use this method for rank detail pages to prevent LazyInitializationException.
     */
    @Query("SELECT r FROM MilitaryRankEntity r LEFT JOIN FETCH r.personMilitaryServices WHERE r.id = :id")
    Optional<MilitaryRankEntity> findByIdWithPersons(@Param("id") Long id);

    // 3. Find by Army Branch (Cleaned convention)
    /**
     * Finds all ranks belonging to organizations associated with a specific Army Branch.
     */
    List<MilitaryRankEntity> findByMilitaryOrganizationArmyBranchId(Long branchId);

    // 4. Find by Military Organization ID (Added for common lookup)
    /**
     * Finds all ranks defined by a specific Military Organization.
     */
    List<MilitaryRankEntity> findByMilitaryOrganizationId(Long organizationId);

    // 5. Find by Military Structure ID (Retained based on your Entity link)
    /**
     * Finds all military ranks that belong to a specific military structure.
     */
    List<MilitaryRankEntity> findByMilitaryStructureId(Long militaryStructureId);
}
