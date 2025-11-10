package cz.rodro.service;

import cz.rodro.dto.InstitutionDTO;
import cz.rodro.dto.mapper.InstitutionMapper;
import cz.rodro.entity.CountryEntity;
import cz.rodro.entity.InstitutionEntity;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.repository.CountryRepository;
import cz.rodro.entity.repository.InstitutionRepository;
import cz.rodro.entity.repository.LocationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Named;

@Service
@RequiredArgsConstructor
public class InstitutionServiceImpl implements InstitutionService {

    private final InstitutionRepository institutionRepository;
    private final InstitutionMapper institutionMapper;
    private final LocationRepository locationRepository;
    private final CountryRepository countryRepository;

    // --- Private Helper Methods for Lookups (Keeping logic in Service) ---

    private CountryEntity findCountryEntity(Long countryId) {
        if (countryId == null) {
            throw new IllegalArgumentException("Country ID must be provided.");
        }
        return countryRepository.findById(countryId)
                .orElseThrow(() -> new EntityNotFoundException("Country not found with id: " + countryId));
    }

    private LocationEntity findLocationEntity(Long locationId) {
        if (locationId == null) {
            throw new IllegalArgumentException("Location ID must be provided.");
        }
        return locationRepository.findById(locationId)
                .orElseThrow(() -> new EntityNotFoundException("Location not found with id: " + locationId));
    }

    // NOTE: mapCountryIdToEntity method has been REMOVED as it is redundant/conflicting.

    // --------------------------------------------------------------------

    @Override
    public InstitutionDTO getInstitutionById(Long id) {
        InstitutionEntity entity = institutionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Institution not found with id: " + id));
        return institutionMapper.toDto(entity);
    }

    @Override
    public List<InstitutionDTO> getAllInstitutions() {
        return institutionRepository.findAll().stream()
                .map(institutionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public InstitutionDTO createInstitution(InstitutionDTO dto) {
        // 1. Map DTO to Entity (Mapper handles fields EXCEPT FK relationships)
        InstitutionEntity entity = institutionMapper.toEntity(dto);

        // 2. SERVICE LOGIC: Look up and Link Country Entity (using flat field: getCountryId)
        CountryEntity country = findCountryEntity(dto.getCountryId());
        entity.setCountry(country);

        // 3. SERVICE LOGIC: Look up and Link Location Entity (using flat field: getLocationId)
        LocationEntity location = findLocationEntity(dto.getLocationId());
        entity.setLocation(location);

        // 4. Save and return
        InstitutionEntity saved = institutionRepository.save(entity);
        return institutionMapper.toDto(saved);
    }

    @Transactional
    @Override
    public InstitutionDTO updateInstitution(Long id, InstitutionDTO dto) {
        InstitutionEntity existing = institutionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Institution not found with id: " + id));

        // 1. Update basic fields using the mapper
        institutionMapper.updateInstitutionEntity(dto, existing);

        // 2. Handle Country update (if ID is present)
        if (dto.getCountryId() != null) {
            CountryEntity country = findCountryEntity(dto.getCountryId());
            existing.setCountry(country);
        }

        // 3. Handle Location update (using flat field: getLocationId)
        if (dto.getLocationId() != null) {
            LocationEntity location = findLocationEntity(dto.getLocationId());
            existing.setLocation(location);
        } else {
            // If DTO explicitly sets LocationId to null, set the entity location to null
            existing.setLocation(null);
        }

        InstitutionEntity updated = institutionRepository.save(existing);
        return institutionMapper.toDto(updated);
    }

    @Override
    public void deleteInstitution(Long id) {
        if (!institutionRepository.existsById(id)) {
            throw new EntityNotFoundException("Institution not found with id: " + id);
        }
        institutionRepository.deleteById(id);
    }
}
