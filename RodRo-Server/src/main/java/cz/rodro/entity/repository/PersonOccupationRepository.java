package cz.rodro.entity.repository;

import cz.rodro.entity.PersonOccupationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonOccupationRepository extends JpaRepository<PersonOccupationEntity, Long> {

    List<PersonOccupationEntity> findByPersonId(Long personId);

    List<PersonOccupationEntity> findByOccupationId(Long occupationId);

  }