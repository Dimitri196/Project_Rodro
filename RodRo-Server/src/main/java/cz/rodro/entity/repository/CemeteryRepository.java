package cz.rodro.entity.repository;

import cz.rodro.entity.CemeteryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface CemeteryRepository extends JpaRepository<CemeteryEntity, Long> {

    List<CemeteryEntity> findByCemeteryLocation_Id(Long locationId);

}