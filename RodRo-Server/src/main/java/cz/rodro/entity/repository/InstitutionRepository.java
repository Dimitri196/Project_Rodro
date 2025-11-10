package cz.rodro.entity.repository;

import cz.rodro.constant.InstitutionType;
import cz.rodro.entity.InstitutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Institution entities.
 * Extends JpaRepository to provide standard CRUD and pagination functionality.
 */
@Repository
public interface InstitutionRepository extends JpaRepository<InstitutionEntity, Long> {

    /**
     * Finds all institutions belonging to a specific country ID.
     * Maps to the 'country' relationship in InstitutionEntity.
     * @param countryId The ID of the CountryEntity to filter by.
     * @return A list of InstitutionEntity objects.
     */
    List<InstitutionEntity> findAllByCountryId(Long countryId);

    /**
     * Finds all institutions matching a specific type.
     * @param type The InstitutionType to filter by.
     * @return A list of InstitutionEntity objects.
     */
    List<InstitutionEntity> findAllByType(InstitutionType type);

    /**
     * Finds all institutions established in a specific year/era (String value).
     * @param establishmentYear The establishment year string (e.g., "1500", "c. 1000 BC").
     * @return A list of InstitutionEntity objects.
     */
    List<InstitutionEntity> findAllByEstablishmentYear(String establishmentYear);

    /**
     * Finds all institutions that are currently active (cancellationYear is null).
     * The `IsNull` keyword checks for a null value in the corresponding column.
     * @return A list of currently active InstitutionEntity objects.
     */
    List<InstitutionEntity> findAllByCancellationYearIsNull();

    /**
     * Finds institutions whose name contains the given string, ignoring case.
     * Useful for search functionality.
     * @param name The substring to search for.
     * @return A list of matching InstitutionEntity objects.
     */
    List<InstitutionEntity> findAllByNameContainingIgnoreCase(String name);

    /**
     * Finds all active institutions of a specific type within a specific country.
     * A practical query for filtering operational institutions.
     * @param countryId The ID of the country.
     * @param type The InstitutionType.
     * @return A list of matching InstitutionEntity objects.
     */
    List<InstitutionEntity> findAllByCountryIdAndTypeAndCancellationYearIsNull(Long countryId, InstitutionType type);
}