package cz.rodro.entity.repository;

import cz.rodro.constant.AttributionType;
import cz.rodro.entity.SourceAttributionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SourceAttributionRepository extends JpaRepository<SourceAttributionEntity, Long> {

    // All attributions that attach to a specific person entity
    List<SourceAttributionEntity> findByPerson_Id(Long personId);

    // Filtered by person and by attribution type
    List<SourceAttributionEntity> findByPerson_IdAndType(Long personId, AttributionType type);

    // Find by occupation link (if you attach to occupation entity)
    List<SourceAttributionEntity> findByOccupation_Id(Long occupationId);

    // Find by family link
    List<SourceAttributionEntity> findByFamily_Id(Long familyId);

    // Find by military service link
    List<SourceAttributionEntity> findByMilitaryService_Id(Long msId);

    @Query("""
    SELECT sa FROM SourceAttributionEntity sa
    LEFT JOIN sa.person p
    LEFT JOIN sa.occupation po
    LEFT JOIN sa.family f
    LEFT JOIN sa.militaryService ms
    WHERE p.id = :personId
       OR po.person.id = :personId
       OR f.spouseMale.id = :personId
       OR f.spouseFemale.id = :personId
       OR ms.person.id = :personId
""")
    List<SourceAttributionEntity> findAllByPersonIdOrLinkedEntities(@Param("personId") Long personId);
}
