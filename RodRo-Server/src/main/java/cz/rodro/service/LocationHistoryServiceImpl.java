package cz.rodro.service;

import cz.rodro.dto.LocationDTO;
import cz.rodro.dto.LocationHistoryDTO;
import cz.rodro.dto.mapper.LocationHistoryMapper;
import cz.rodro.dto.mapper.LocationMapper;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.LocationHistoryEntity;
import cz.rodro.entity.SubdivisionEntity;
import cz.rodro.entity.repository.LocationHistoryRepository;
import cz.rodro.entity.repository.LocationRepository;
import cz.rodro.entity.repository.SubdivisionRepository;
import cz.rodro.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationHistoryServiceImpl implements LocationHistoryService {

    @Autowired
    private LocationHistoryRepository locationHistoryRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private SubdivisionRepository subdivisionRepository; // Added to fetch subdivision

    @Autowired
    private LocationHistoryMapper locationHistoryMapper;

    @Autowired
    private LocationMapper locationMapper;

    @Override
    public List<LocationHistoryDTO> getHistoryForLocation(Long locationId) {
        List<LocationHistoryEntity> historyEntities = locationHistoryRepository.findByLocationId(locationId);
        return historyEntities.stream()
                .map(locationHistoryMapper::toLocationHistoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LocationHistoryDTO addLocationHistory(Long locationId, LocationHistoryDTO locationHistoryDTO) {
        LocationEntity location = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Location not found"));

        // Handle subdivision if it's provided in the DTO
        SubdivisionEntity subdivision = null;
        if (locationHistoryDTO.getSubdivisionId() != null) {
            subdivision = subdivisionRepository.findById(locationHistoryDTO.getSubdivisionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Subdivision not found"));
        }

        // Map DTO to entity and set the location and subdivision
        LocationHistoryEntity historyEntity = locationHistoryMapper.toLocationHistoryEntity(locationHistoryDTO);
        historyEntity.setLocation(location);
        historyEntity.setSubdivision(subdivision);

        historyEntity = locationHistoryRepository.save(historyEntity);
        return locationHistoryMapper.toLocationHistoryDTO(historyEntity);
    }

    @Override
    public LocationHistoryDTO updateLocationHistory(Long historyId, LocationHistoryDTO locationHistoryDTO) {
        LocationHistoryEntity existingEntity = locationHistoryRepository.findById(historyId)
                .orElseThrow(() -> new ResourceNotFoundException("Location history not found"));

        // Handle subdivision update
        if (locationHistoryDTO.getSubdivisionId() != null) {
            SubdivisionEntity subdivision = subdivisionRepository.findById(locationHistoryDTO.getSubdivisionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Subdivision not found"));
            existingEntity.setSubdivision(subdivision);
        }

        // Update other fields in the entity
        locationHistoryMapper.updateLocationHistoryEntity(locationHistoryDTO, existingEntity);

        LocationHistoryEntity updatedEntity = locationHistoryRepository.save(existingEntity);
        return locationHistoryMapper.toLocationHistoryDTO(updatedEntity);
    }

    @Override
    public void deleteLocationHistory(Long historyId) {
        if (!locationHistoryRepository.existsById(historyId)) {
            throw new ResourceNotFoundException("Location history not found");
        }
        locationHistoryRepository.deleteById(historyId);
    }

    @Override
    public List<LocationHistoryDTO> getHistoryForDistrict(Long districtId) {
        List<LocationHistoryEntity> historyEntities = locationHistoryRepository.findByDistrictId(districtId);
        return historyEntities.stream()
                .map(locationHistoryMapper::toLocationHistoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LocationHistoryDTO> getHistoryForSubdivision(Long subdivisionId) {  // New method for subdivision
        List<LocationHistoryEntity> historyEntities = locationHistoryRepository.findBySubdivisionId(subdivisionId);
        return historyEntities.stream()
                .map(locationHistoryMapper::toLocationHistoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LocationDTO> getLocationsBySubdivisionId(Long subdivisionId) {
        List<LocationEntity> locations = locationHistoryRepository.findDistinctLocationsBySubdivisionId(subdivisionId);
        return locations.stream()
                .map(locationMapper::toLocationDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LocationDTO> getLocationsByDistrictId(Long districtId) {
        List<LocationEntity> locations = locationHistoryRepository.findDistinctLocationsByDistrictId(districtId);
        // Assuming you have a LocationMapper injected and ready
        return locations.stream()
                .map(locationMapper::toLocationDTO)
                .collect(Collectors.toList());
    }
}
