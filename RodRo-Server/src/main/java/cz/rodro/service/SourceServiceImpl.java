package cz.rodro.service;

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

import java.util.List;

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
    @Transactional(readOnly = true)
    public Page<SourceListDTO> getAllSourcesAsDTO(String searchTerm, Pageable pageable) {
        Page<SourceListProjection> projectionPage = sourceRepository.findAllSourcesProjected(searchTerm, pageable);

        List<SourceListDTO> dtos = projectionPage.stream()
                .map(sourceListMapper::toDTO)
                .toList();

        return new PageImpl<>(dtos, pageable, projectionPage.getTotalElements());
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