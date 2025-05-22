package cz.rodro.service;

import cz.rodro.dto.ParishLocationDTO;
import cz.rodro.dto.mapper.ParishLocationMapper;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.ParishLocationEntity;
import cz.rodro.entity.repository.LocationRepository;
import cz.rodro.entity.repository.ParishLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParishLocationServiceImpl implements ParishLocationService {

    private final ParishLocationRepository parishLocationRepository;
    private final ParishLocationMapper parishLocationMapper;
    private final LocationRepository locationRepository;

    @Autowired
    public ParishLocationServiceImpl(
            ParishLocationRepository parishLocationRepository,
            ParishLocationMapper parishLocationMapper,
            LocationRepository locationRepository) {
        this.parishLocationRepository = parishLocationRepository;
        this.parishLocationMapper = parishLocationMapper;
        this.locationRepository = locationRepository;
    }

    @Override
    public List<ParishLocationDTO> getParishesByLocationId(Long locationId) {
        List<ParishLocationEntity> entities = parishLocationRepository.findByLocationId(locationId);
        return entities.stream()
                .map(parishLocationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ParishLocationDTO addParishToLocation(Long locationId, ParishLocationDTO parishLocationDTO) {
        ParishLocationEntity entity = parishLocationMapper.toEntity(parishLocationDTO);
        LocationEntity location = locationRepository.findById(locationId)
                .orElseThrow(() -> new IllegalArgumentException("Location not found"));
        entity.setLocation(location);
        ParishLocationEntity saved = parishLocationRepository.save(entity);
        return parishLocationMapper.toDto(saved);
    }

    @Override
    public void removeParishFromLocation(Long locationId, Long parishId) {
        parishLocationRepository.deleteByLocationIdAndParishId(locationId, parishId);
    }
}