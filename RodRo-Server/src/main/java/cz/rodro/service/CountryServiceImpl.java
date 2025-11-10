package cz.rodro.service;

import cz.rodro.dto.CountryContinentHistoryDTO;
import cz.rodro.dto.CountryDTO;
import cz.rodro.dto.ProvinceDTO;
import cz.rodro.dto.mapper.CountryMapper;
import cz.rodro.dto.mapper.ProvinceMapper;
import cz.rodro.entity.CountryEntity;
import cz.rodro.entity.ProvinceEntity;
import cz.rodro.entity.repository.CountryRepository;
import cz.rodro.entity.repository.ProvinceRepository;
import cz.rodro.exception.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for managing Country-related business logic.
 * This layer handles data fetching, transactional boundaries, and security access control.
 */
@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final ProvinceRepository provinceRepository;
    private final CountryMapper countryMapper;
    private final ProvinceMapper provinceMapper;
    private final CountryContinentHistoryService historyService;

    /**
     * Constructor Injection for dependencies. Spring automatically handles the @Autowired
     * annotation when using a single constructor.
     */
    public CountryServiceImpl(
            CountryRepository countryRepository,
            ProvinceRepository provinceRepository,
            CountryMapper countryMapper,
            ProvinceMapper provinceMapper,
            CountryContinentHistoryService historyService

    ) {
        this.countryRepository = countryRepository;
        this.provinceRepository = provinceRepository;
        this.countryMapper = countryMapper;
        this.provinceMapper = provinceMapper;
        this.historyService = historyService;
    }

    // --- READ Operations ---

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("permitAll()")
    public List<CountryDTO> getAll() {
        return countryRepository
                .findAll()
                .stream()
                .map(countryMapper::toCountryDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("permitAll()")
    public CountryDTO getCountry(long countryId) {
        // 1. Fetch the main Country Entity
        CountryEntity countryEntity = countryRepository.findById(countryId)
                .orElseThrow(() -> new ResourceNotFoundException("Country", "id", countryId));

        // 2. Map Entity to DTO
        CountryDTO countryDTO = countryMapper.toCountryDTO(countryEntity);

        // 3. Fetch and attach Province data
        // FIX: Renamed method to use Spring Data JPA convention for navigating the 'country' object
        List<ProvinceEntity> provinces = provinceRepository.findByCountry_Id(countryId);

        // MapStruct handles the nested DTO mapping (including countryId/countryName flattening)
        List<ProvinceDTO> provinceDTOs = provinces.stream()
                .map(provinceMapper::toProvinceDTO)
                .collect(Collectors.toList());
        countryDTO.setProvinces(provinceDTOs);

        // 4. --- NEW LOGIC: FETCH AND ATTACH CONTINENT HISTORY ---

        // Call the dedicated history service to get the temporal data
        List<CountryContinentHistoryDTO> historyDTOs = historyService.getHistoryByCountry(countryId);

        // Attach the history data to the Country DTO
        countryDTO.setContinentHistory(historyDTOs);

        return countryDTO;
    }

    // --- WRITE Operations ---

    /**
     * {@inheritDoc}
     * Persists a new Country to the database.
     */
    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public CountryDTO addCountry(CountryDTO countryDTO) {

        countryDTO.setId(null);
        CountryEntity countryEntity = countryMapper.toCountryEntity(countryDTO);
        countryEntity = countryRepository.save(countryEntity);

        return countryMapper.toCountryDTO(countryEntity);
    }

    /**
     * {@inheritDoc}
     * Updates an existing Country record identified by its ID.
     * * @implNote Uses MapStruct's update method to retain entity state (e.g., provinces)
     * not present in the DTO, preventing data loss.
     */
    @Override
    @Transactional // Logic: Ensures fetch/update/save operations are atomic
    @PreAuthorize("hasRole('ADMIN')") // Security: Only administrators can update countries
    public CountryDTO updateCountry(Long countryId, CountryDTO countryDTO) {
        CountryEntity existingEntity = countryRepository.findById(countryId)
                .orElseThrow(() -> new ResourceNotFoundException("Country", "id", countryId));

        countryMapper.updateCountryEntityFromDTO(countryDTO, existingEntity);
        existingEntity.setId(countryId);
        CountryEntity savedCountryEntity = countryRepository.save(existingEntity);

        return countryMapper.toCountryDTO(savedCountryEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public CountryEntity fetchCountryById(Long id, String type) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(type, "id", id));
    }


}
