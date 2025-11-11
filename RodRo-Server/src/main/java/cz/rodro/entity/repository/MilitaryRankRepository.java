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

    /**
     * Finds ranks by their categorical level (e.g., OFFICER, ENLISTED).
     */
    List<MilitaryRankEntity> findByRankLevel(MilitaryRankLevel rankLevel);

    /**
     * Finds a rank by ID and eagerly fetches its associated personnel records.
     * Use this method for rank detail pages to prevent LazyInitializationException.
     */
    @Query("SELECT r FROM MilitaryRankEntity r LEFT JOIN FETCH r.personMilitaryServices WHERE r.id = :id")
    Optional<MilitaryRankEntity> findByIdWithPersons(@Param("id") Long id);

    /**
     * Finds all ranks belonging to organizations associated with a specific Army Branch.
     */
    List<MilitaryRankEntity> findByMilitaryOrganizationArmyBranchId(Long branchId);

    /**
     * Finds all ranks defined by a specific Military Organization.
     */
    List<MilitaryRankEntity> findByMilitaryOrganizationId(Long organizationId);

    /**
     * Finds all military ranks that belong to a specific military structure.
     */
    List<MilitaryRankEntity> findByMilitaryStructureId(Long militaryStructureId);
}
