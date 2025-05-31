package cz.rodro.entity.repository;

import cz.rodro.entity.MilitaryArmyBranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MilitaryArmyBranchRepository extends JpaRepository<MilitaryArmyBranchEntity, Long> {

    Optional<MilitaryArmyBranchEntity> findByArmyBranchName(String name);

    List<MilitaryArmyBranchEntity> findByArmyBranchNameContainingIgnoreCase(String partialName);
}
