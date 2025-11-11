package cz.rodro.service;

import cz.rodro.constant.ConfessionType;
import cz.rodro.constant.SourceType;
import cz.rodro.dto.SourceDTO;
import cz.rodro.dto.SourceListDTO;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.SourceEntity;
import cz.rodro.exception.ResourceNotFoundException;
import cz.rodro.dto.mapper.SourceListMapper;
import cz.rodro.dto.mapper.SourceMapper;
import cz.rodro.dto.SourceListProjection;
import cz.rodro.entity.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link SourceService} handling CRUD operations and paginated list retrieval
 * for Source entities, with mapping to frontend-ready DTOs.
 */
@Service
public class SourceServiceImpl implements SourceService {

    private final SourceRepository sourceRepository;
    private final SourceMapper sourceMapper;
    private final SourceListMapper sourceListMapper;
    private final LocationService locationService;

    @Autowired
    public SourceServiceImpl(SourceRepository sourceRepository,
                             SourceMapper sourceMapper,
                             SourceListMapper sourceListMapper,
                             LocationService locationService) {
        this.sourceRepository = sourceRepository;
        this.sourceMapper = sourceMapper;
        this.sourceListMapper = sourceListMapper;
        this.locationService = locationService;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SourceListDTO> getAllSourcesAsDTO(Map<String, String> params, Pageable pageable) {

        // --- 1. Extract and Normalize String Filters (Diacritic-Insensitive Search) ---
        String titleFilterString = params.get("filter_title");
        String referenceFilterString = params.get("filter_reference");
        String locationNameFilterString = params.get("filter_locationName");

        // Pass 'null' to the repository if the string is empty or blank.
        // The value stored here is the normalized, lowercase, diacritic-free string sent by the frontend.
        String finalTitle = StringUtils.hasText(titleFilterString) ? titleFilterString : null;
        String finalReference = StringUtils.hasText(referenceFilterString) ? referenceFilterString : null;
        String finalLocationName = StringUtils.hasText(locationNameFilterString) ? locationNameFilterString : null;

        // --- 2. Extract and Convert Enum Filters (SourceType and ConfessionType) ---

        // SourceType
        String typeFilterString = params.get("filter_type");
        SourceType finalType = null;
        if (StringUtils.hasText(typeFilterString)) {
            try {
                finalType = SourceType.valueOf(typeFilterString.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid SourceType value received: " + typeFilterString + ". Skipping filter.");
            }
        }

        // ✨ NEW: ConfessionType Filter Extraction and Conversion
        String confessionFilterString = params.get("filter_confession");
        ConfessionType finalConfession = null;
        if (StringUtils.hasText(confessionFilterString)) {
            try {
                // Use the newly defined ConfessionType enum
                finalConfession = ConfessionType.valueOf(confessionFilterString.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid ConfessionType value received: " + confessionFilterString + ". Skipping filter.");
            }
        }

        // --- 3. Extract and Convert Numeric/Range Filters (creationYearMin) ---

        // ✨ NEW: Creation Year Minimum Filter
        String creationYearMinString = params.get("filter_creationYearMin");
        Integer finalCreationYearMin = null;
        if (StringUtils.hasText(creationYearMinString)) {
            try {
                // Safely parse the string to an Integer
                finalCreationYearMin = Integer.parseInt(creationYearMinString);
            } catch (NumberFormatException e) {
                System.err.println("Invalid creationYearMin value received: " + creationYearMinString + ". Skipping filter.");
            }
        }


        // --- 4. Call the updated Repository Method ---
        // Pass ALL filter parameters to the repository
        Page<SourceListProjection> projectionPage = sourceRepository.findAllSourcesProjected(
                finalTitle,
                finalReference,
                finalType,
                finalConfession,        // ✨ NEW PARAMETER
                finalCreationYearMin,   // ✨ NEW PARAMETER
                finalLocationName,
                pageable
        );

        // --- 5. Map Projections to DTOs and Return Page ---
        List<SourceListDTO> dtos = projectionPage.stream()
                .map(sourceListMapper::toDTO)
                .toList();

        return new PageImpl<>(dtos, pageable, projectionPage.getTotalElements());
    }

    // --- Other CRUD Methods (Kept for completeness) ---

    @Override
    @Transactional
    public SourceDTO addSource(SourceDTO sourceDTO) {
        LocationEntity location = null;
        if (sourceDTO.getLocationId() != null) {
            location = locationService.fetchLocationById(sourceDTO.getLocationId(), "Source Location");
        }

        SourceEntity sourceEntity = sourceMapper.toSourceEntity(sourceDTO);
        sourceEntity.setLocation(location);

        SourceEntity saved = sourceRepository.save(sourceEntity);
        return sourceMapper.toSourceDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public SourceDTO getSource(Long sourceId) {
        SourceEntity entity = fetchSourceById(sourceId);
        return sourceMapper.toSourceDTO(entity);
    }

    @Override
    @Transactional
    public SourceDTO updateSource(Long sourceId, SourceDTO sourceDTO) {
        SourceEntity existing = fetchSourceById(sourceId);

        // Update primitive fields via MapStruct
        sourceMapper.updateSourceEntity(sourceDTO, existing);

        // Update location if provided
        LocationEntity newLocation = null;
        if (sourceDTO.getLocationId() != null) {
            newLocation = locationService.fetchLocationById(sourceDTO.getLocationId(), "Source Location");
        }
        existing.setLocation(newLocation);

        SourceEntity updated = sourceRepository.save(existing);
        return sourceMapper.toSourceDTO(updated);
    }

    @Override
    @Transactional
    public void removeSource(Long sourceId) {
        SourceEntity entity = fetchSourceById(sourceId);
        sourceRepository.delete(entity);
    }

    /**
     * Utility method to fetch a SourceEntity by ID or throw ResourceNotFoundException.
     *
     * @param id the source ID
     * @return the SourceEntity
     */
    private SourceEntity fetchSourceById(Long id) {
        return sourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Source with ID " + id + " not found"));
    }
}