package cz.rodro.entity.repository;

import cz.rodro.entity.PersonEntity;
import cz.rodro.entity.PersonSourceEvidenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonSourceEvidenceRepository extends JpaRepository<PersonSourceEvidenceEntity, Long> {

    void deleteAllByPerson(PersonEntity person);

    List<PersonSourceEvidenceEntity> findByPerson(PersonEntity person);

    List<PersonSourceEvidenceEntity> findByPersonId(Long personId);
}
