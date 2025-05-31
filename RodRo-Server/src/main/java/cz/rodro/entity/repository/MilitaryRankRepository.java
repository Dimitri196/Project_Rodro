package cz.rodro.entity.repository;

import cz.rodro.entity.MilitaryRankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MilitaryRankRepository extends JpaRepository<MilitaryRankEntity, Long> {

    List<MilitaryRankEntity> findByArmyBranch_Id(Long branchId);

    List<MilitaryRankEntity> findByRankLevel(String rankLevel);
}