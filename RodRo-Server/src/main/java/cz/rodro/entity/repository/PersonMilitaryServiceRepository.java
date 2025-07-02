package cz.rodro.entity.repository;

import cz.rodro.dto.MilitaryRankDTO;
import cz.rodro.entity.PersonMilitaryServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonMilitaryServiceRepository extends JpaRepository<PersonMilitaryServiceEntity, Long> {
    List<PersonMilitaryServiceEntity> findByPersonId(Long personId);

    List<PersonMilitaryServiceEntity> findAllByMilitaryRankId(Long rankId);

}