package cz.rodro.entity.repository;

import cz.rodro.entity.ParishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParishRepository extends JpaRepository<ParishEntity, Long> {

    List<ParishEntity> findByParishNameContainingIgnoreCase(String parishName);

}
