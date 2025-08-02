package cz.rodro.service;

import cz.rodro.dto.SourceDTO;
import cz.rodro.dto.SourceListProjection;
import cz.rodro.dto.mapper.SourceMapper;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.SourceEntity;
import cz.rodro.entity.repository.SourceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import cz.rodro.exception.NotFoundException;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for managing {@link SourceEntity} and {@link SourceDTO} operations.
 * Handles business logic, data mapping, and transaction management for sources.
 */
@Service
public class SourceServiceImpl implements SourceService {

    private final SourceRepository sourceRepository;
    private final SourceMapper sourceMapper;
    private final LocationService locationService; // Inject LocationService to fetch LocationEntity

    @Autowired
    public SourceServiceImpl(SourceRepository sourceRepository, SourceMapper sourceMapper, LocationService locationService) {
        this.sourceRepository = sourceRepository;
        this.sourceMapper = sourceMapper;
        this.locationService = locationService;
    }

    @Override
    @Transactional
    public SourceDTO addSource(SourceDTO sourceDTO) {
        // Fetch the LocationEntity if sourceLocationId is provided in the DTO
        LocationEntity sourceLocation = null;
        if (sourceDTO.getSourceLocationId() != null) {
            sourceLocation = locationService.fetchLocationById(sourceDTO.getSourceLocationId(), "Source Location");
        }

        SourceEntity sourceEntity = sourceMapper.toSourceEntity(sourceDTO);
        sourceEntity.setSourceLocation(sourceLocation); // Set the resolved LocationEntity

        SourceEntity saved = sourceRepository.save(sourceEntity);
        return sourceMapper.toSourceDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public SourceDTO getSource(long sourceId) { // Changed parameter type to long
        SourceEntity entity = fetchSourceById(sourceId);
        return sourceMapper.toSourceDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SourceListProjection> getAllSources(String searchTerm, Pageable pageable) {
        // Uses the optimized projection method from the repository
        return sourceRepository.findAllSourcesProjected(searchTerm, pageable);
    }

    @Override
    @Transactional
    public SourceDTO updateSource(Long sourceId, SourceDTO sourceDTO) { // Changed parameter name to sourceId
        SourceEntity existing = fetchSourceById(sourceId);

        // Update primitive fields using MapStruct
        sourceMapper.updateSourceEntity(sourceDTO, existing);

        // Update the associated LocationEntity if the ID changes
        LocationEntity newSourceLocation = null;
        if (sourceDTO.getSourceLocationId() != null) {
            newSourceLocation = locationService.fetchLocationById(sourceDTO.getSourceLocationId(), "Source Location");
        }
        existing.setSourceLocation(newSourceLocation);

        SourceEntity updated = sourceRepository.save(existing);
        return sourceMapper.toSourceDTO(updated);
    }

    @Override
    @Transactional
    public void removeSource(long sourceId) { // Changed method name to removeSource and parameter type to long
        SourceEntity entity = fetchSourceById(sourceId);
        sourceRepository.delete(entity);
    }

    // Private utility method to fetch with exception
    private SourceEntity fetchSourceById(Long id) {
        return sourceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Source with ID " + id + " not found"));
    }
}
