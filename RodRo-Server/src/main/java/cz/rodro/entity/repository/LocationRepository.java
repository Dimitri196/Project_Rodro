package cz.rodro.entity.repository;

import cz.rodro.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<LocationEntity, Long>  {

}
