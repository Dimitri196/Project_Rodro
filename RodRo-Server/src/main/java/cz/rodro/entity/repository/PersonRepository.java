package cz.rodro.entity.repository;

import cz.rodro.dto.PersonListProjection;
import cz.rodro.entity.PersonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable; // Ensure this specific import is used
import java.util.List;

/**
 * Repository interface for accessing {@link PersonEntity} data.
 * Provides methods for CRUD operations and custom queries related to person entities.
 */
@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    /**
     * Finds all {@link PersonEntity} instances with a given identification number.
     *
     * @param identificationNumber The identification number of the person.
     * @return A list of {@link PersonEntity} instances matching the provided identification number.
     */
    List<PersonEntity> findByIdentificationNumber(String identificationNumber);

    /**
     * Finds all children of a given parent.
     *
     * @param parent The parent PersonEntity.
     * @return A list of PersonEntity instances who have the provided person as a father or mother.
     */
    @Query("SELECT p FROM person p WHERE p.father = :parent OR p.mother = :parent")
    List<PersonEntity> findChildrenByParent(@Param("parent") PersonEntity parent);

    /**
     * Finds all children of a parent by their ID.
     *
     * @param id The ID of the parent.
     * @return A list of PersonEntity instances who have the provided person ID as a father or mother.
     */
    @Query("SELECT p FROM person p WHERE p.father.id = :id OR p.mother.id = :id")
    List<PersonEntity> findChildrenByParentId(@Param("id") Long id);

    /**
     * Finds persons by both father's and mother's IDs.
     *
     * @param fatherId The ID of the father.
     * @param motherId The ID of the mother.
     * @return A list of PersonEntity instances matching the provided father and mother IDs.
     */
    List<PersonEntity> findByFatherIdAndMotherId(Long fatherId, Long motherId);


    /**
     * Finds a paginated, filtered, and sorted list of persons,
     * returning them as a lightweight PersonListProjection.
     * This query uses JPQL to select specific fields and perform LEFT JOINs
     * to fetch related location names directly, avoiding N+1 problems
     * and reducing the data payload for list views.
     *
     * @param searchTerm An optional term to filter by given name, surname, or identification number.
     * If null or empty, no text filtering is applied.
     * @param pageable   Pagination and sorting information (page number, size, sort order).
     * @return A Page of PersonListProjection containing the requested subset of data.
     */
    @Query(value = "SELECT p.id AS id, p.givenName AS givenName, p.givenSurname AS givenSurname, " +
            "p.identificationNumber AS identificationNumber, " +
            "p.birthYear AS birthYear, p.birthMonth AS birthMonth, p.birthDay AS birthDay, " +
            "p.deathYear AS deathYear, p.deathMonth AS deathMonth, p.deathDay AS deathDay, " +
            "bp.locationName AS birthPlaceName, " +
            "bap.locationName AS baptizationPlaceName, " +
            "dp.locationName AS deathPlaceName, " +
            "burp.locationName AS burialPlaceName " +
            "FROM person p " + // 'person' refers to the entity name, not necessarily the table name
            "LEFT JOIN p.birthPlace bp " +
            "LEFT JOIN p.baptizationPlace bap " +
            "LEFT JOIN p.deathPlace dp " +
            "LEFT JOIN p.burialPlace burp " +
            "WHERE (:searchTerm IS NULL OR :searchTerm = '' OR " +
            "       LOWER(p.givenName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "       LOWER(p.givenSurname) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "       LOWER(p.identificationNumber) LIKE LOWER(CONCAT('%', :searchTerm, '%')))",
            countQuery = "SELECT COUNT(p.id) FROM person p " +
                    "LEFT JOIN p.birthPlace bp " + // Need joins in count query too for correct filtering
                    "LEFT JOIN p.baptizationPlace bap " +
                    "LEFT JOIN p.deathPlace dp " +
                    "LEFT JOIN p.burialPlace burp " +
                    "WHERE (:searchTerm IS NULL OR :searchTerm = '' OR " +
                    "       LOWER(p.givenName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
                    "       LOWER(p.givenSurname) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
                    "       LOWER(p.identificationNumber) LIKE LOWER(CONCAT('%', :searchTerm, '%')))"
    )
    Page<PersonListProjection> findAllPersonsProjected(
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );
}
