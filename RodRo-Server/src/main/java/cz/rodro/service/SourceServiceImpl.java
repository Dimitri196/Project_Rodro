package cz.rodro.service;

import cz.rodro.dto.SourceDTO;
import cz.rodro.dto.mapper.LocationHistoryMapper;
import cz.rodro.dto.mapper.SourceMapper;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.SourceEntity;
import cz.rodro.entity.repository.LocationRepository;
import cz.rodro.entity.repository.SourceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cz.rodro.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SourceServiceImpl implements SourceService {

    @Autowired
    private SourceRepository sourceRepository;

    @Autowired
    private SourceMapper sourceMapper;

    @Autowired
    private LocationService locationService;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationHistoryMapper locationHistoryMapper;

    @Override
    public SourceDTO addSource(SourceDTO sourceDTO) {

        LocationEntity sourceLocation = locationService.fetchLocationById(sourceDTO.getSourceLocation().getId(), "Source Location");

        SourceEntity sourceEntity = sourceMapper.toSourceEntity(sourceDTO);
        sourceEntity.setSourceLocation(sourceLocation);

        SourceEntity saved = sourceRepository.save(sourceEntity);
        return sourceMapper.toSourceDTO(saved);
    }

    @Override
    public SourceDTO getSource(Long id) {
        SourceEntity entity = fetchSourceById(id);
        return sourceMapper.toSourceDTO(entity);
    }

    @Override
    public List<SourceDTO> getAllSources() {
        return sourceRepository.findAll()
                .stream()
                .map(sourceMapper::toSourceDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SourceDTO updateSource(Long id, SourceDTO sourceDTO) {
        SourceEntity existing = fetchSourceById(id);
        sourceMapper.updateSourceEntity(sourceDTO, existing);
        SourceEntity updated = sourceRepository.save(existing);
        return sourceMapper.toSourceDTO(updated);
    }

    @Override
    public void deleteSource(Long id) {
        SourceEntity entity = fetchSourceById(id);
        sourceRepository.delete(entity);
    }

    // Private utility method to fetch with exception
    private SourceEntity fetchSourceById(Long id) {
        return sourceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Source with ID " + id + " not found"));
    }
}
