package cz.rodro.service;

import cz.rodro.dto.CountryDTO;
import cz.rodro.dto.ProvinceDTO;
import cz.rodro.dto.mapper.CountryMapper;
import cz.rodro.dto.mapper.ProvinceMapper;
import cz.rodro.entity.CountryEntity;
import cz.rodro.entity.ProvinceEntity;
import cz.rodro.entity.repository.CountryRepository;
import cz.rodro.entity.repository.ProvinceRepository;
import cz.rodro.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private CountryMapper countryMapper;

    @Autowired
    private ProvinceMapper provinceMapper;

    @Override
    public List<CountryDTO> getAll() {
        return countryRepository
                .findAll()
                .stream()
                .map(i -> countryMapper.toCountryDTO(i))
                .collect(Collectors.toList());
    }

    @Override
    public CountryDTO getCountry(long countryId) {
        CountryEntity countryEntity = countryRepository.findById(countryId)
                .orElseThrow(() -> new RuntimeException("Country not found"));

        // Fetch provinces of the country
        List<ProvinceEntity> provinces = provinceRepository.findByCountryId(countryId);

        CountryDTO countryDTO = countryMapper.toCountryDTO(countryEntity);
        List<ProvinceDTO> provinceDTOs = provinces.stream()
                .map(provinceMapper::toProvinceDTO)
                .collect(Collectors.toList());

        // Set the provinces in the CountryDTO
        countryDTO.setProvinces(provinceDTOs);

        return countryDTO;
    }

    @Override
    public CountryDTO addCountry(CountryDTO countryDTO) {
        // Convert CountryDTO to CountryEntity using the newly added method
        CountryEntity countryEntity = countryMapper.toCountryEntity(countryDTO);
        countryEntity = countryRepository.save(countryEntity);
        return countryMapper.toCountryDTO(countryEntity);
    }

    @Override
    @Transactional
    public CountryDTO updateCountry(Long countryId, CountryDTO countryDTO) {
        if(!countryRepository.existsById(countryId)) {
            throw new NotFoundException("Country with id " + countryId + " wasn't found in the database");
        }
        // Convert CountryDTO to CountryEntity using the newly added method
        CountryEntity countryEntity = countryMapper.toCountryEntity(countryDTO);
        countryEntity.setId(countryId);
        CountryEntity savedCountryEntity = countryRepository.save(countryEntity);
        return countryMapper.toCountryDTO(savedCountryEntity);
    }

    @Override
    public CountryEntity fetchCountryById(Long id, String type) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(type + " with id " + id + " wasn't found in the database."));
    }
}
