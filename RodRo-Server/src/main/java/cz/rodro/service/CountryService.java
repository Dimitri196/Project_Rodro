package cz.rodro.service;

import cz.rodro.dto.CountryDTO;
import cz.rodro.entity.CountryEntity;

import java.util.List;

public interface CountryService {

    List<CountryDTO> getAll();
    CountryDTO getCountry(long countryId);
    CountryDTO addCountry(CountryDTO countryDTO);
    CountryDTO updateCountry(Long countryId, CountryDTO countryDTO);
    CountryEntity fetchCountryById(Long id, String type);

}
