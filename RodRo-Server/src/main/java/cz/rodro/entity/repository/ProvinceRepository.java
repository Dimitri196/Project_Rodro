package cz.rodro.entity.repository;

import cz.rodro.entity.ProvinceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProvinceRepository extends JpaRepository<ProvinceEntity, Long> {

    /**
     * Finds all ProvinceEntity records associated with a specific country ID.
     * Spring Data JPA navigates the 'country' field to access its 'id'.
     *
     * @param countryId The ID of the parent CountryEntity.
     * @return A list of ProvinceEntity objects.
     */
    List<ProvinceEntity> findByCountry_Id(Long countryId);
}