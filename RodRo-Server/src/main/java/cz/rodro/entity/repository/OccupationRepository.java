package cz.rodro.entity.repository;

import cz.rodro.entity.OccupationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OccupationRepository extends JpaRepository<OccupationEntity, Long> {

    @Query("SELECT o.id FROM OccupationEntity o WHERE o.institution.id = :institutionId")
    List<Long> findOccupationsByInstitutionId(@Param("institutionId") Long institutionId);

    @Query("SELECT o FROM OccupationEntity o LEFT JOIN FETCH o.personOccupations po LEFT JOIN FETCH po.person WHERE o.id = :id")
    Optional<OccupationEntity> findByIdWithPersons(@Param("id") Long id);
}