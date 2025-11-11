package cz.rodro.entity.repository;

import cz.rodro.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing CountryEntity data in the database.
 * This interface extends JpaRepository, inheriting standard CRUD (Create, Read, Update, Delete)
 * and pagination methods.
 *
 * @implNote Custom query methods for complex filtering or searching should be defined here,
 * but for basic operations, no further code is needed.
 */
public interface CountryRepository extends JpaRepository<CountryEntity, Long> {

}