package cz.rodro.service;

import cz.rodro.dto.CountryContinentHistoryDTO;
import cz.rodro.dto.mapper.CountryContinentHistoryMapper;
import cz.rodro.entity.ContinentEntity;
import cz.rodro.entity.CountryContinentHistoryEntity;
import cz.rodro.entity.CountryEntity;
import cz.rodro.entity.repository.ContinentRepository;
import cz.rodro.entity.repository.CountryContinentHistoryRepository;
import cz.rodro.entity.repository.CountryRepository;
import cz.rodro.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryContinentHistoryServiceImpl implements CountryContinentHistoryService {

    private final CountryContinentHistoryRepository historyRepository;
    private final CountryRepository countryRepository;
    private final ContinentRepository continentRepository;
    private final CountryContinentHistoryMapper historyMapper;

    @Autowired
    public CountryContinentHistoryServiceImpl(
            CountryContinentHistoryRepository historyRepository,
            CountryRepository countryRepository,
            ContinentRepository continentRepository,
            CountryContinentHistoryMapper historyMapper) {
        this.historyRepository = historyRepository;
        this.countryRepository = countryRepository;
        this.continentRepository = continentRepository;
        this.historyMapper = historyMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CountryContinentHistoryDTO> getHistoryByCountry(Long countryId) {
        // Validation: Ensure country exists before querying history
        if (!countryRepository.existsById(countryId)) {
            throw new ResourceNotFoundException("Country", "id", countryId);
        }

        List<CountryContinentHistoryEntity> historyEntities =
                historyRepository.findByCountryIdOrderByStartYearAsc(countryId);

        return historyEntities.stream()
                .map(historyMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CountryContinentHistoryDTO addHistoryRecord(CountryContinentHistoryDTO dto) {

        // 1. Fetch related entities using IDs from DTO
        CountryEntity country = countryRepository.findById(dto.getCountryId())
                .orElseThrow(() -> new ResourceNotFoundException("Country", "id", dto.getCountryId()));

        ContinentEntity continent = continentRepository.findById(dto.getContinentId())
                .orElseThrow(() -> new ResourceNotFoundException("Continent", "id", dto.getContinentId()));

        // 2. Map simple fields and set relationships
        CountryContinentHistoryEntity historyEntity = historyMapper.toEntity(dto);
        historyEntity.setCountry(country);
        historyEntity.setContinent(continent);

        // 3. Save and map back to DTO
        historyEntity = historyRepository.save(historyEntity);
        return historyMapper.toDTO(historyEntity);
    }

    @Override
    @Transactional
    public CountryContinentHistoryDTO updateHistoryRecord(Long id, CountryContinentHistoryDTO dto) {
        CountryContinentHistoryEntity existingEntity = historyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Country Continent History", "id", id));

        // 1. Update simple fields (startYear, endYear)
        // We explicitly ignore countryId and continentId from the DTO in the mapper update method
        historyMapper.updateEntityFromDTO(dto, existingEntity);

        // 2. Save and map back to DTO
        CountryContinentHistoryEntity updatedEntity = historyRepository.save(existingEntity);
        return historyMapper.toDTO(updatedEntity);
    }

    @Override
    @Transactional
    public void deleteHistoryRecord(Long id) {
        if (!historyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Country Continent History", "id", id);
        }
        historyRepository.deleteById(id);
    }
}
