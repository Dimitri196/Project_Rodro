package cz.rodro.entity.repository;

import cz.rodro.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository interface for accessing {@link PersonEntity} data.
 * Provides methods for CRUD operations and custom queries related to person entities.
 */
    public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    /**
     * Finds all {@link PersonEntity} instances with a given identification number.
     *
     * @param identificationNumber The identification number of the person.
     * @return A list of {@link PersonEntity} instances matching the provided identification number.
     */
    List<PersonEntity> findByIdentificationNumber(String identificationNumber);

    @Query("SELECT p FROM person p WHERE p.father = :parent OR p.mother = :parent")
    List<PersonEntity> findChildrenByParent(@Param("parent") PersonEntity parent);

    }

