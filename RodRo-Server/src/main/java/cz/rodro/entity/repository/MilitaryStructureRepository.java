package cz.rodro.entity.repository;

import cz.rodro.entity.MilitaryStructureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MilitaryStructureRepository extends JpaRepository<MilitaryStructureEntity, Long> {

    // Find all structures under a given organization
    List<MilitaryStructureEntity> findByOrganization_Id(Long organizationId);

    // Find by unit name (partial search)
    List<MilitaryStructureEntity> findByUnitNameContainingIgnoreCase(String unitName);

    // Find by active year
    @Query("SELECT s FROM MilitaryStructureEntity s WHERE :year BETWEEN s.activeFromYear AND s.activeToYear")
    List<MilitaryStructureEntity> findStructuresActiveInYear(@Param("year") String year);

}
