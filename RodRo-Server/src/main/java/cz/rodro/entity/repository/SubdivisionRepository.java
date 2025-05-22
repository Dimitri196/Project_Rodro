package cz.rodro.entity.repository;

import cz.rodro.entity.SubdivisionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubdivisionRepository extends JpaRepository<SubdivisionEntity, Long> {

    List<SubdivisionEntity> findByDistrictId(Long districtId);

    @Query("SELECT s FROM SubdivisionEntity s LEFT JOIN FETCH s.historyEntries WHERE s.id = :id")
    SubdivisionEntity findByIdWithHistoryEntries(@Param("id") Long id);
}