package cz.rodro.entity.repository;

import cz.rodro.entity.CountryContinentHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryContinentHistoryRepository extends JpaRepository<CountryContinentHistoryEntity, Long> {

    /**
     * Finds all continent associations for a specific country, ordered by start year.
     * @param countryId The ID of the country.
     * @return List of history records.
     */
    List<CountryContinentHistoryEntity> findByCountryIdOrderByStartYearAsc(Long countryId);

    /**
     * Finds all history records associated with a specific continent.
     * @param continentId The ID of the continent.
     * @return List of history records.
     */
    List<CountryContinentHistoryEntity> findByContinentId(Long continentId);
}
