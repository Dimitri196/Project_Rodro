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

    List<MilitaryStructureEntity> findByOrganization_Id(Long organizationId);

    List<MilitaryStructureEntity> findByUnitNameContainingIgnoreCase(String unitName);

    @Query("SELECT s FROM MilitaryStructureEntity s WHERE :year BETWEEN s.activeFromYear AND s.activeToYear")
    List<MilitaryStructureEntity> findStructuresActiveInYear(@Param("year") String year);

    List<MilitaryStructureEntity> findByOrganization_IdAndOrganization_ArmyBranch_Id(Long orgId, Long branchId);

    List<MilitaryStructureEntity> findByOrganization_ArmyBranch_Id(Long branchId);

    // New methods to support the self-referential parent/sub-structure relationships

    /**
     * Finds all military structures that are sub-units of a given parent structure.
     * @param parentStructureId The ID of the parent structure.
     * @return A list of sub-structures.
     */
    List<MilitaryStructureEntity> findByParentStructure_Id(Long parentStructureId);

    /**
     * Finds a specific military structure by its ID and eagerly fetches its parent.
     * Useful for building a breadcrumb navigation.
     * @param id The ID of the structure.
     * @return An Optional containing the MilitaryStructureEntity.
     */
    @Query("SELECT s FROM MilitaryStructureEntity s LEFT JOIN FETCH s.parentStructure WHERE s.id = :id")
    Optional<MilitaryStructureEntity> findByIdWithParent(@Param("id") Long id);

    /**
     * Finds all top-level military structures within an organization (i.e., those without a parent structure).
     * @param organizationId The ID of the organization.
     * @return A list of top-level military structures.
     */
    @Query("SELECT s FROM MilitaryStructureEntity s WHERE s.organization.id = :organizationId AND s.parentStructure IS NULL")
    List<MilitaryStructureEntity> findTopLevelStructuresByOrganizationId(@Param("organizationId") Long organizationId);

    /**
     * Finds a military structure by ID and eagerly fetches its associated organization and army branch.
     * This prevents LazyInitializationException and ensures all necessary data is loaded in one query.
     * @param id The ID of the structure.
     * @return An Optional containing the MilitaryStructureEntity with its organization and branch loaded.
     */
    @Query("SELECT s FROM MilitaryStructureEntity s JOIN FETCH s.organization o JOIN FETCH o.armyBranch WHERE s.id = :id")
    Optional<MilitaryStructureEntity> findByIdWithOrganizationAndBranch(@Param("id") Long id);
}
