package cz.rodro.entity.repository;

import cz.rodro.constant.AttributionType;
import cz.rodro.entity.SourceAttributionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SourceAttributionRepository extends JpaRepository<SourceAttributionEntity, Long> {

    List<SourceAttributionEntity> findByPerson_Id(Long personId);

    List<SourceAttributionEntity> findByPerson_IdAndType(Long personId, AttributionType type);

    List<SourceAttributionEntity> findByOccupation_Id(Long occupationId);

    List<SourceAttributionEntity> findByFamily_Id(Long familyId);

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
