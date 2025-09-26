package cz.rodro.service;

import cz.rodro.dto.LocationDTO;
import cz.rodro.dto.LocationHistoryDTO;
import cz.rodro.dto.LocationListProjection;
import cz.rodro.dto.SourceDTO;
import cz.rodro.dto.mapper.LocationHistoryMapper;
import cz.rodro.dto.mapper.LocationMapper;
import cz.rodro.dto.mapper.SourceMapper;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.LocationHistoryEntity;
import cz.rodro.entity.SourceEntity;
import cz.rodro.entity.repository.LocationHistoryRepository;
import cz.rodro.entity.repository.LocationRepository;
import cz.rodro.entity.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import cz.rodro.exception.NotFoundException;
import org.springframework.transaction.annotation.Transactional; // Correct import for Spring's @Transactional


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Service implementation for managing {@link LocationEntity} and {@link LocationDTO} operations.
 * Handles business logic, data mapping, and transaction management for locations.
 */
@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final LocationHistoryMapper locationHistoryMapper; // Injected for history DTO/Entity conversion
    private final SourceMapper sourceMapper; // Injected for source DTO/Entity conversion
    private final LocationHistoryRepository locationHistoryRepository; // Injected for managing history entities
    private final SourceRepository sourceRepository; // Injected for managing source entities

    @Autowired
    public LocationServiceImpl(
            LocationRepository locationRepository,
            LocationMapper locationMapper,
            LocationHistoryMapper locationHistoryMapper,
            SourceMapper sourceMapper,
            LocationHistoryRepository locationHistoryRepository,
            SourceRepository sourceRepository) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
        this.locationHistoryMapper = locationHistoryMapper;
        this.sourceMapper = sourceMapper;
        this.locationHistoryRepository = locationHistoryRepository;
        this.sourceRepository = sourceRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LocationListProjection> getAllLocations(String searchTerm, Pageable pageable) {
        // Uses the optimized projection method from the repository
        return locationRepository.findAllLocationsProjected(searchTerm, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public LocationDTO getLocation(long locationId) {
        // Uses the EntityGraph method to eagerly fetch histories and sources
        // Renamed findByIdWithDetails to findById and specified the entity graph in the repository
        LocationEntity locationEntity = locationRepository.findById(locationId)
                .orElseThrow(() -> new NotFoundException("Location with id " + locationId + " wasn't found in the database."));
        return locationMapper.toLocationDTO(locationEntity);
    }

    @Override
    @Transactional
    public LocationDTO addLocation(LocationDTO locationDTO) {
        // Use a new variable for the entity being built to ensure it's effectively final for lambdas
        LocationEntity newLocationEntity = locationMapper.toLocationEntity(locationDTO);

        // Handle LocationHistoryEntity creation and association
        if (locationDTO.getLocationHistories() != null && !locationDTO.getLocationHistories().isEmpty()) {
            List<LocationHistoryEntity> histories = locationDTO.getLocationHistories().stream()
                    .map(historyDTO -> {
                        LocationHistoryEntity historyEntity = locationHistoryMapper.toLocationHistoryEntity(historyDTO);
                        historyEntity.setLocation(newLocationEntity); // Use the effectively final newLocationEntity
                        return historyEntity;
                    })
                    .collect(Collectors.toList());
            newLocationEntity.setLocationHistories(histories);
        } else {
            newLocationEntity.setLocationHistories(new ArrayList<>()); // Initialize to empty list instead of null
        }

        // Handle SourceEntity creation and association
        if (locationDTO.getSources() != null && !locationDTO.getSources().isEmpty()) {
            List<SourceEntity> sources = locationDTO.getSources().stream()
                    .map(sourceDTO -> {
                        SourceEntity sourceEntity = sourceMapper.toSourceEntity(sourceDTO);
                        sourceEntity.setLocation(newLocationEntity); // Use the effectively final newLocationEntity
                        return sourceEntity;
                    })
                    .collect(Collectors.toList());
            newLocationEntity.setSources(sources);
        } else {
            newLocationEntity.setSources(new ArrayList<>()); // Initialize to empty list instead of null
        }

        // Save the new entity, and capture the result in a new variable
        LocationEntity savedLocationEntity = locationRepository.save(newLocationEntity);
        return locationMapper.toLocationDTO(savedLocationEntity);
    }

    @Override
    @Transactional
    public void removeLocation(long locationId) {
        LocationEntity locationEntity = locationRepository.findById(locationId)
                .orElseThrow(() -> new NotFoundException("Location with id " + locationId + " wasn't found in the database."));
        locationRepository.delete(locationEntity);
    }

    @Override
    @Transactional
    public LocationDTO updateLocation(Long locationId, LocationDTO locationDTO) {
        // Fetch with details to ensure collections are loaded and manageable within the transaction
        // Renamed findByIdWithDetails to findById and specified the entity graph in the repository
        LocationEntity existingLocation = locationRepository.findById(locationId)
                .orElseThrow(() -> new NotFoundException("Location with id " + locationId + " wasn't found in the database."));

        // Update primitive fields using MapStruct
        locationMapper.updateLocationEntity(locationDTO, existingLocation);

        // --- Manage LocationHistoryEntity collection ---
        List<LocationHistoryEntity> currentHistories = existingLocation.getLocationHistories();
        List<LocationHistoryDTO> incomingHistories = locationDTO.getLocationHistories() != null ?
                locationDTO.getLocationHistories() : List.of();

        // Identify histories to remove (present in current but not in incoming)
        currentHistories.removeIf(currentHistory -> incomingHistories.stream()
                .noneMatch(incomingHistory -> Objects.equals(incomingHistory.getId(), currentHistory.getId())));

        // Add or update histories
        for (LocationHistoryDTO incomingHistoryDTO : incomingHistories) {
            if (incomingHistoryDTO.getId() == null) {
                // New history
                LocationHistoryEntity newHistory = locationHistoryMapper.toLocationHistoryEntity(incomingHistoryDTO);
                newHistory.setLocation(existingLocation);
                currentHistories.add(newHistory);
            } else {
                // Existing history - find and update
                currentHistories.stream()
                        .filter(currentHistory -> Objects.equals(currentHistory.getId(), incomingHistoryDTO.getId()))
                        .findFirst()
                        .ifPresent(historyToUpdate -> locationHistoryMapper.updateLocationHistoryEntity(incomingHistoryDTO, historyToUpdate));
            }
        }

        // --- Manage SourceEntity collection ---
        List<SourceEntity> currentSources = existingLocation.getSources();
        List<SourceDTO> incomingSources = locationDTO.getSources() != null ?
                locationDTO.getSources() : List.of();

        // Identify sources to remove (present in current but not in incoming)
        currentSources.removeIf(currentSource -> incomingSources.stream()
                .noneMatch(incomingSource -> Objects.equals(incomingSource.getId(), currentSource.getId())));

        // Add or update sources
        for (SourceDTO incomingSourceDTO : incomingSources) {
            if (incomingSourceDTO.getId() == null) {
                // New source - assuming SourceDTO can create a new SourceEntity
                SourceEntity newSource = sourceMapper.toSourceEntity(incomingSourceDTO);
                newSource.setLocation(existingLocation); // Set parent
                currentSources.add(newSource);
            } else {
                // Existing source - find and update
                // This assumes you want to update properties of the SourceEntity itself.
                // If SourceDTO only references existing SourceEntities, you might just need to ensure the link exists.
                currentSources.stream()
                        .filter(currentSource -> Objects.equals(currentSource.getId(), incomingSourceDTO.getId()))
                        .findFirst()
                        .ifPresent(sourceToUpdate -> sourceMapper.updateSourceEntity(incomingSourceDTO, sourceToUpdate));
            }
        }

        LocationEntity updatedLocation = locationRepository.save(existingLocation); // Save the parent entity to cascade changes
        return locationMapper.toLocationDTO(updatedLocation);
    }

    @Override
    @Transactional(readOnly = true)
    public LocationEntity fetchLocationById(Long id, String type) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(type + " with id " + id + " wasn't found in the database."));
    }
}
