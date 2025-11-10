package cz.rodro.entity.repository;

import cz.rodro.entity.MilitaryStructureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MilitaryStructureRepository extends JpaRepository<MilitaryStructureEntity, Long> {

    List<MilitaryStructureEntity> findByOrganizationId(Long organizationId);

    List<MilitaryStructureEntity> findByNameContainingIgnoreCase(String name);

    @Query("SELECT s FROM MilitaryStructureEntity s WHERE :year BETWEEN s.activeFromYear AND s.activeToYear")
    List<MilitaryStructureEntity> findStructuresActiveInYear(@Param("year") String year);

    List<MilitaryStructureEntity> findByOrganizationIdAndOrganizationArmyBranchId(Long orgId, Long branchId);

    List<MilitaryStructureEntity> findByOrganizationArmyBranchId(Long branchId);

    /**
     * Finds a military structure by ID and eagerly fetches its associated organization and army branch.
     * This prevents LazyInitializationException and ensures all necessary data is loaded in one query.
     */
    @Query("SELECT s FROM MilitaryStructureEntity s JOIN FETCH s.organization o JOIN FETCH o.armyBranch WHERE s.id = :id")
    Optional<MilitaryStructureEntity> findByIdWithOrganizationAndBranch(@Param("id") Long id);
}
