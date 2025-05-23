package cz.rodro.entity.repository;

import cz.rodro.entity.MilitaryServiceSourceEvidenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MilitaryServiceSourceEvidenceRepository extends JpaRepository<MilitaryServiceSourceEvidenceEntity, Long> {

    // Get all source evidences for a given military service record
    @Query("SELECT m FROM MilitaryServiceSourceEvidenceEntity m WHERE m.militaryService.id = :serviceId")
    List<MilitaryServiceSourceEvidenceEntity> findByMilitaryServiceId(@Param("serviceId") Long serviceId);

    // Get all source evidences for a given source
    @Query("SELECT m FROM MilitaryServiceSourceEvidenceEntity m WHERE m.source.id = :sourceId")
    List<MilitaryServiceSourceEvidenceEntity> findBySourceId(@Param("sourceId") Long sourceId);

    // Optional: Find all by keyword in notes
    @Query("SELECT m FROM MilitaryServiceSourceEvidenceEntity m WHERE LOWER(m.note) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<MilitaryServiceSourceEvidenceEntity> searchByNote(@Param("keyword") String keyword);

    // Optional: Combine filters — all sources for a given person
    @Query("""
        SELECT mse FROM MilitaryServiceSourceEvidenceEntity mse
        WHERE mse.militaryService.person.id = :personId
    """)
    List<MilitaryServiceSourceEvidenceEntity> findByPersonId(@Param("personId") Long personId);
}
