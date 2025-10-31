package cz.rodro.entity.repository;

import cz.rodro.dto.PersonInLocationDTO;
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
     */
    List<PersonEntity> findByExternalId(String externalId);

    /**
     * Finds all children of a given parent.
     */
    @Query("SELECT p FROM person p WHERE p.father = :parent OR p.mother = :parent")
    List<PersonEntity> findChildrenByParent(@Param("parent") PersonEntity parent);

    /**
     * Finds all children of a parent by their ID.
     */
    @Query("SELECT p FROM person p WHERE p.father.id = :id OR p.mother.id = :id")
    List<PersonEntity> findChildrenByParentId(@Param("id") Long id);

    /**
     * Finds persons by both father's and mother's IDs.
     */
    List<PersonEntity> findByFatherIdAndMotherId(Long fatherId, Long motherId);

    /**
     * Paginated and filtered list of persons (projection).
     * Diacritic-insensitive search for names and case-insensitive for ID.
     */
    @Query(value = """

            SELECT p.id AS id, 
               p.givenName AS givenName, 
               p.surname AS surname,
               p.externalId AS externalId,
               p.birthYear AS birthYear, 
               p.birthMonth AS birthMonth, 
               p.birthDay AS birthDay,
               p.deathYear AS deathYear, 
               p.deathMonth AS deathMonth, 
               p.deathDay AS deathDay,
               bp.locationName AS birthPlaceName,
               bap.locationName AS baptizationPlaceName,
               dp.locationName AS deathPlaceName,
               burp.locationName AS burialPlaceName
        FROM person p
        LEFT JOIN p.birthPlace bp
        LEFT JOIN p.baptismPlace bap
        LEFT JOIN p.deathPlace dp
        LEFT JOIN p.burialPlace burp
        WHERE (:searchTerm IS NULL OR :searchTerm = '' OR 
              LOWER(FUNCTION('REPLACE', FUNCTION('REPLACE', p.givenName, 'ł','l'), 'Ł','L')) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
              LOWER(FUNCTION('REPLACE', FUNCTION('REPLACE', p.surname, 'ł','l'), 'Ł','L')) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
              LOWER(p.externalId) LIKE LOWER(CONCAT('%', :searchTerm, '%')))
        """,
            countQuery = """
        SELECT COUNT(p.id)
        FROM person p
        LEFT JOIN p.birthPlace bp
        LEFT JOIN p.baptismPlace bap
        LEFT JOIN p.deathPlace dp
        LEFT JOIN p.burialPlace burp
        WHERE (:searchTerm IS NULL OR :searchTerm = '' OR 
              LOWER(FUNCTION('REPLACE', FUNCTION('REPLACE', p.givenName, 'ł','l'), 'Ł','L')) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
              LOWER(FUNCTION('REPLACE', FUNCTION('REPLACE', p.surname, 'ł','l'), 'Ł','L')) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR
              LOWER(p.externalId) LIKE LOWER(CONCAT('%', :searchTerm, '%')))
        """
    )
    Page<PersonListProjection> findAllPersonsProjected(
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );

    /**
     * Quick diacritic-insensitive search for names (used in global search).
     */
    @Query("""
        SELECT p FROM person p
        WHERE LOWER(FUNCTION('REPLACE', FUNCTION('REPLACE', p.givenName, 'ł','l'), 'Ł','L')) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
           OR LOWER(FUNCTION('REPLACE', FUNCTION('REPLACE', p.surname, 'ł','l'), 'Ł','L')) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
    """)
    List<PersonEntity> findByNormalizedSearchTerm(@Param("searchTerm") String searchTerm);

// --- Queries for Location Detail Page (using PersonInLocationDTO) ---

    /**
     * Finds persons who were BORN at the given location.
     */
    @Query("SELECT new cz.rodro.dto.PersonInLocationDTO(p.id, p.givenName, p.surname, p.birthYear, p.deathYear) " +
            "FROM person p WHERE p.birthPlace.id = :locationId " +
            "ORDER BY p.surname, p.givenName")
    List<PersonInLocationDTO> findByBirthPlaceId(@Param("locationId") Long locationId);

    /**
     * Finds persons who DIED at the given location.
     */
    @Query("SELECT new cz.rodro.dto.PersonInLocationDTO(p.id, p.givenName, p.surname, p.birthYear, p.deathYear) " +
            "FROM person p WHERE p.deathPlace.id = :locationId " +
            "ORDER BY p.surname, p.givenName")
    List<PersonInLocationDTO> findByDeathPlaceId(@Param("locationId") Long locationId);

    /**
     * Finds persons who were BURIED at the given location.
     */
    @Query("SELECT new cz.rodro.dto.PersonInLocationDTO(p.id, p.givenName, p.surname, p.birthYear, p.deathYear) " +
            "FROM person p WHERE p.burialPlace.id = :locationId " +
            "ORDER BY p.surname, p.givenName")
    List<PersonInLocationDTO> findByBurialPlaceId(@Param("locationId") Long locationId);

    /**
     * Finds persons who were BAPTISED at the given location.
     */
    @Query("SELECT new cz.rodro.dto.PersonInLocationDTO(p.id, p.givenName, p.surname, p.birthYear, p.deathYear) " +
            "FROM person p WHERE p.baptismPlace.id = :locationId " +
            "ORDER BY p.surname, p.givenName")
    List<PersonInLocationDTO> findByBaptismPlaceId(@Param("locationId") Long locationId);



    // =========================================================================
    // --- DEMOGRAPHIC AND SCIENTIFIC STATISTICAL QUERIES ---
    // =========================================================================

    /**
     * Calculates the average lifespan (in years-of-life) grouped by gender.
     */
    @Query("SELECT p.gender, AVG(p.deathYear - p.birthYear) " +
            "FROM person p " +
            "WHERE p.deathYear IS NOT NULL AND p.birthYear IS NOT NULL " +
            "AND p.gender IS NOT NULL " +
            "GROUP BY p.gender")
    List<Object[]> calculateAverageLifespanByGender();

    /**
     * Counts the number of deaths grouped by the death location name.
     */
    @Query("SELECT dp.locationName, COUNT(p.id) " +
            "FROM person p " +
            "JOIN p.deathPlace dp " +
            "WHERE p.deathYear IS NOT NULL " +
            "GROUP BY dp.locationName " +
            "ORDER BY COUNT(p.id) DESC")
    List<Object[]> countDeathsByLocation();

    /**
     * Counts the total number of births grouped by birth year (vitality trend).
     */
    @Query("SELECT p.birthYear, COUNT(p) " +
            "FROM person p " +
            "WHERE p.birthYear IS NOT NULL " +
            "GROUP BY p.birthYear " +
            "ORDER BY p.birthYear ASC")
    List<Object[]> countBirthsByYear();

    /**
     * Counts the total number of deaths grouped by death year (mortality trend/Crude Death Rate proxy).
     */
    @Query("SELECT p.deathYear, COUNT(p) " +
            "FROM person p " +
            "WHERE p.deathYear IS NOT NULL " +
            "GROUP BY p.deathYear " +
            "ORDER BY p.deathYear ASC")
    List<Object[]> countDeathsByYear();

    /**
     * Counts the number of births grouped by the birth location name.
     */
    @Query("SELECT bp.locationName, COUNT(p.id) " +
            "FROM person p " +
            "JOIN p.birthPlace bp " +
            "WHERE p.birthYear IS NOT NULL " +
            "GROUP BY bp.locationName " +
            "ORDER BY COUNT(p.id) DESC")
    List<Object[]> countBirthsByLocation();

    /**
     * Counts the number of people grouped by their Social Status.
     */
    @Query("SELECT p.socialStatus, COUNT(p) " +
            "FROM person p " +
            "WHERE p.socialStatus IS NOT NULL " +
            "GROUP BY p.socialStatus " +
            "ORDER BY COUNT(p) DESC")
    List<Object[]> countPeopleBySocialStatus();

    /**
     * Counts the occurrences of each Cause of Death, further grouped by Gender (for stacked charts).
     */
    @Query("SELECT p.causeOfDeath, p.gender, COUNT(p) " +
            "FROM person p " +
            "WHERE p.causeOfDeath IS NOT NULL AND p.gender IS NOT NULL " +
            "GROUP BY p.causeOfDeath, p.gender " +
            "ORDER BY p.causeOfDeath, p.gender")
    List<Object[]> countCauseOfDeathByGender();

    /**
     * Calculates the age at death (year-of-life) and groups the count into predefined 10-year buckets.
     * The results are ordered by the implicit age group sort key (e.g., '0. Infant', '1. 01-10').
     */
    @Query("SELECT " +
            "CASE " +
            "   WHEN (p.deathYear - p.birthYear) < 1 THEN '0. Infant' " +
            "   WHEN (p.deathYear - p.birthYear) BETWEEN 1 AND 10 THEN '1. 01-10' " +
            "   WHEN (p.deathYear - p.birthYear) BETWEEN 11 AND 20 THEN '2. 11-20' " +
            "   WHEN (p.deathYear - p.birthYear) BETWEEN 21 AND 30 THEN '3. 21-30' " +
            "   WHEN (p.deathYear - p.birthYear) BETWEEN 31 AND 40 THEN '4. 31-40' " +
            "   WHEN (p.deathYear - p.birthYear) BETWEEN 41 AND 50 THEN '5. 41-50' " +
            "   WHEN (p.deathYear - p.birthYear) BETWEEN 51 AND 60 THEN '6. 51-60' " +
            "   WHEN (p.deathYear - p.birthYear) BETWEEN 61 AND 70 THEN '7. 61-70' " +
            "   WHEN (p.deathYear - p.birthYear) BETWEEN 71 AND 80 THEN '8. 71-80' " +
            "   WHEN (p.deathYear - p.birthYear) > 80 THEN '9. 80+' " +
            "   ELSE 'A. Unknown' END AS ageGroup, " +
            "COUNT(p) " +
            "FROM person p " +
            "WHERE p.deathYear IS NOT NULL AND p.birthYear IS NOT NULL " +
            "GROUP BY ageGroup " +
            "ORDER BY ageGroup")
    List<Object[]> countDeathsByAgeGroup();

    /**
     * Counts the number of deaths grouped by the death place's SettlementType.
     */
    @Query("SELECT dp.settlementType, COUNT(p.id) " +
            "FROM person p " +
            "JOIN p.deathPlace dp " +
            "WHERE p.deathYear IS NOT NULL AND dp.settlementType IS NOT NULL " +
            "GROUP BY dp.settlementType " +
            "ORDER BY COUNT(p.id) DESC")
    List<Object[]> countDeathsBySettlementType();


    // -------------------------------------------------------------------------
    // --- ADVANCED SCIENTIFIC MORTALITY QUERIES ---
    // -------------------------------------------------------------------------

    /**
     * Counts **infant deaths** (age < 1 year) by the death year.
     * Crucial for calculating the Infant Mortality Rate (IMR).
     * Infant death is approximated as dying in the same calendar year as birth.
     * Results: [Death Year (Integer), Infant Death Count (Long)]
     */
    @Query("""
        SELECT 
            p.deathYear, 
            COUNT(p.id) 
        FROM person p 
        WHERE p.deathYear IS NOT NULL AND p.birthYear IS NOT NULL
          AND (p.deathYear - p.birthYear) = 0
        GROUP BY p.deathYear 
        ORDER BY 1
    """)
    List<Object[]> countInfantDeathsByYear();


    /**
     * Provides **multi-dimensional death data** grouped by Location, Year, and Age Group.
     * This single, robust query allows the Service layer to construct Mortality Tables,
     * Age-Specific Death Rates, and complex Histograms.
     *
     * @return List of Object arrays:
     * [Death Location Name (String), Death Year (Integer), Age Group Label (String), Count (Long)]
     */
    @Query("""
        SELECT
            l.locationName,
            p.deathYear,
            CASE 
                WHEN p.deathYear IS NULL OR p.birthYear IS NULL THEN 'A. Unknown Age'
                WHEN (p.deathYear - p.birthYear) = 0 THEN '0. Infant'
                WHEN (p.deathYear - p.birthYear) >= 1 AND (p.deathYear - p.birthYear) <= 10 THEN '1. 01-10'
                WHEN (p.deathYear - p.birthYear) > 10 AND (p.deathYear - p.birthYear) <= 20 THEN '2. 11-20'
                WHEN (p.deathYear - p.birthYear) > 20 AND (p.deathYear - p.birthYear) <= 30 THEN '3. 21-30'
                WHEN (p.deathYear - p.birthYear) > 30 AND (p.deathYear - p.birthYear) <= 40 THEN '4. 31-40'
                WHEN (p.deathYear - p.birthYear) > 40 AND (p.deathYear - p.birthYear) <= 50 THEN '5. 41-50'
                WHEN (p.deathYear - p.birthYear) > 50 AND (p.deathYear - p.birthYear) <= 60 THEN '6. 51-60'
                WHEN (p.deathYear - p.birthYear) > 60 AND (p.deathYear - p.birthYear) <= 70 THEN '7. 61-70'
                WHEN (p.deathYear - p.birthYear) > 70 AND (p.deathYear - p.birthYear) <= 80 THEN '8. 71-80'
                WHEN (p.deathYear - p.birthYear) > 80 THEN '9. 80+'
                ELSE 'A. Unknown Age' 
            END,
            COUNT(p.id)
        FROM person p
        JOIN p.deathPlace l  
        WHERE p.deathYear IS NOT NULL AND p.birthYear IS NOT NULL
          AND l.locationName IS NOT NULL
        GROUP BY l.locationName, p.deathYear, 3  
        ORDER BY p.deathYear, l.locationName, 3
    """)
    List<Object[]> countDeathsByAllDimensions();

    /**
     * Counts the occurrences of each specific Cause of Death.
     * This query returns the detailed enum value (String in DB) and count.
     * The aggregation into the high-level DeathCauseCategory must be done in the Service Layer.
     */
    @Query("""
    SELECT 
        p.causeOfDeath, 
        COUNT(p.id) 
    FROM person p 
    WHERE p.causeOfDeath IS NOT NULL 
    GROUP BY p.causeOfDeath 
    ORDER BY 2 DESC
    """)
    List<Object[]> countCauseOfDeathsDetailed();

    /**
     * Calculates the age at death and groups the count into predefined age buckets,
     * filtered for a specific location.
     */
    @Query("""
    SELECT 
        CASE 
            WHEN (p.deathYear - p.birthYear) < 1 THEN '0. Infant' 
            WHEN (p.deathYear - p.birthYear) BETWEEN 1 AND 10 THEN '1. 01-10' 
            WHEN (p.deathYear - p.birthYear) BETWEEN 11 AND 20 THEN '2. 11-20' 
            WHEN (p.deathYear - p.birthYear) BETWEEN 21 AND 30 THEN '3. 21-30' 
            WHEN (p.deathYear - p.birthYear) BETWEEN 31 AND 40 THEN '4. 31-40' 
            WHEN (p.deathYear - p.birthYear) BETWEEN 41 AND 50 THEN '5. 41-50' 
            WHEN (p.deathYear - p.birthYear) BETWEEN 51 AND 60 THEN '6. 51-60' 
            WHEN (p.deathYear - p.birthYear) BETWEEN 61 AND 70 THEN '7. 61-70' 
            WHEN (p.deathYear - p.birthYear) BETWEEN 71 AND 80 THEN '8. 71-80' 
            WHEN (p.deathYear - p.birthYear) > 80 THEN '9. 80+' 
            ELSE 'A. Unknown Age' 
        END AS ageGroup,
        COUNT(p.id) AS deathCount
    FROM person p
    JOIN p.deathPlace l
    WHERE p.deathYear IS NOT NULL 
      AND p.birthYear IS NOT NULL
      AND l.locationName = :locationName
    GROUP BY ageGroup  
    ORDER BY ageGroup
""")
    List<Object[]> countDeathsByAgeGroupAndLocation(@Param("locationName") String locationName);
}
