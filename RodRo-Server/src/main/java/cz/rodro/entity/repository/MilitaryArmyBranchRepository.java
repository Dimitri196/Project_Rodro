package cz.rodro.entity.repository;

import cz.rodro.entity.MilitaryArmyBranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MilitaryArmyBranchRepository extends JpaRepository<MilitaryArmyBranchEntity, Long> {

    // FIX: Changed findByArmyBranchName to findByName
    Optional<MilitaryArmyBranchEntity> findByName(String name);

    // FIX: Changed findByArmyBranchNameContainingIgnoreCase to findByNameContainingIgnoreCase
    List<MilitaryArmyBranchEntity> findByNameContainingIgnoreCase(String partialName);
}