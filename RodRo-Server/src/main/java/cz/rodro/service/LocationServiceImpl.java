package cz.rodro.service;

import cz.rodro.dto.*;
import cz.rodro.dto.mapper.LocationHistoryMapper;
import cz.rodro.dto.mapper.LocationMapper;
import cz.rodro.dto.mapper.SourceMapper;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.LocationHistoryEntity;
import cz.rodro.entity.SourceEntity;
import cz.rodro.entity.repository.LocationHistoryRepository;
import cz.rodro.entity.repository.LocationRepository;
import cz.rodro.entity.repository.SourceRepository;
import cz.rodro.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Correct import for Spring's @Transactional


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final LocationHistoryMapper locationHistoryMapper;
    private final SourceMapper sourceMapper;
    private final LocationHistoryRepository locationHistoryRepository;
    private final SourceRepository sourceRepository; // Needed for fetching existing SourceEntity

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
        return locationRepository.findAllLocationsProjected(searchTerm, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public LocationDTO getLocation(long locationId) {
        // No change needed here. LocationMapper must be configured to convert
        // LocationEntity.getSources() to List<SourceSummaryDTO>.
        LocationEntity locationEntity = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Location with id " + locationId + " wasn't found in the database."));
        return locationMapper.toLocationDTO(locationEntity);
    }

    @Override
    @Transactional
    public LocationDTO addLocation(LocationDTO locationDTO) {
        LocationEntity newLocationEntity = locationMapper.toLocationEntity(locationDTO);

        // --- Handle LocationHistoryEntity creation and association (Remains the same) ---
        if (locationDTO.getLocationHistories() != null && !locationDTO.getLocationHistories().isEmpty()) {
            List<LocationHistoryEntity> histories = locationDTO.getLocationHistories().stream()
                    .map(historyDTO -> {
                        LocationHistoryEntity historyEntity = locationHistoryMapper.toLocationHistoryEntity(historyDTO);
                        historyEntity.setLocation(newLocationEntity);
                        return historyEntity;
                    })
                    .collect(Collectors.toList());
            newLocationEntity.setLocationHistories(histories);
        } else {
            newLocationEntity.setLocationHistories(new ArrayList<>());
        }

        // ❌ REMOVED: Creation of nested SourceEntity from DTO, as LocationDTO now only contains SourceSummaryDTOs,
        // which lack mandatory fields needed for a full SourceEntity creation.
        // We assume sources must be created separately and linked via updateLocation.
        newLocationEntity.setSources(new ArrayList<>());

        LocationEntity savedLocationEntity = locationRepository.save(newLocationEntity);
        return locationMapper.toLocationDTO(savedLocationEntity);
    }

    @Override
    @Transactional
    public void removeLocation(long locationId) {
        LocationEntity locationEntity = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Location with id " + locationId + " wasn't found in the database."));
        locationRepository.delete(locationEntity);
    }

    @Override
    @Transactional
    public LocationDTO updateLocation(Long locationId, LocationDTO locationDTO) {
        LocationEntity existingLocation = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("Location with id " + locationId + " wasn't found in the database."));

        locationMapper.updateLocationEntity(locationDTO, existingLocation);

        // --- Manage LocationHistoryEntity collection (Remains the same) ---
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

        // --- Manage SourceEntity collection (Link Management Only via SourceSummaryDTO) ---
        List<SourceEntity> currentSources = existingLocation.getSources();

        // ✅ The incoming DTO must now be cast/typed to SourceSummaryDTO list
        // Assuming LocationDTO.getSources() returns List<SourceSummaryDTO>:
        List<SourceSummaryDTO> incomingSources = (List<SourceSummaryDTO>) (List<?>) (locationDTO.getSources() != null ?
                locationDTO.getSources() : List.of());
        // NOTE: This cast requires that your LocationDTO now uses List<SourceSummaryDTO>
        // If your DTO doesn't allow a List<SourceDTO> property, this cast is necessary but fragile.

        // 1. Identify and Remove source links (present in current but not in incoming DTO IDs)
        currentSources.removeIf(currentSource -> incomingSources.stream()
                // Use ID from the lightweight DTO for comparison
                .noneMatch(incomingSource -> Objects.equals(incomingSource.getId(), currentSource.getId())));

        // 2. Add source links (present in incoming DTO IDs but not in current Entity links)
        for (SourceSummaryDTO incomingSourceDTO : incomingSources) {
            if (incomingSourceDTO.getId() != null) {
                // Check if the link already exists in the current list
                boolean linkExists = currentSources.stream()
                        .anyMatch(currentSource -> Objects.equals(currentSource.getId(), incomingSourceDTO.getId()));

                if (!linkExists) {
                    // Link is missing: Fetch the SourceEntity by ID and establish the link
                    SourceEntity sourceToAdd = sourceRepository.findById(incomingSourceDTO.getId())
                            .orElseThrow(() -> new ResourceNotFoundException("Source with id " + incomingSourceDTO.getId() + " not found. Cannot link."));

                    sourceToAdd.setLocation(existingLocation);
                    currentSources.add(sourceToAdd);
                }
            }
            // ❌ REMOVED: sourceMapper.updateSourceEntity(incomingSourceDTO, sourceToUpdate)
            // Cannot update SourceEntity details using SourceSummaryDTO.
        }

        LocationEntity updatedLocation = locationRepository.save(existingLocation);
        return locationMapper.toLocationDTO(updatedLocation);
    }

    @Override
    @Transactional(readOnly = true)
    public LocationEntity fetchLocationById(Long id, String type) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(type + " with id " + id + " wasn't found in the database."));
    }
}