package cz.rodro.entity.repository;

import cz.rodro.entity.MilitaryRankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MilitaryRankRepository extends JpaRepository<MilitaryRankEntity, Long> {

    List<MilitaryRankEntity> findByArmyBranch_Id(Long branchId);

    List<MilitaryRankEntity> findByRankLevel(String rankLevel);

    @Query("SELECT r FROM MilitaryRankEntity r JOIN FETCH r.personMilitaryServices WHERE r.id = :id")
    MilitaryRankEntity findWithPersons(@Param("id") Long id);
}