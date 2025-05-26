package cz.rodro.entity.repository;

import cz.rodro.entity.MilitaryStructureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MilitaryStructureRepository extends JpaRepository<MilitaryStructureEntity, Long> {
}
