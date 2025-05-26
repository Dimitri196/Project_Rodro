package cz.rodro.entity.repository;

import cz.rodro.entity.MilitaryRankEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MilitaryRankRepository extends JpaRepository<MilitaryRankEntity, Long> {
}