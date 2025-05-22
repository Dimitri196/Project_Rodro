package cz.rodro.service;

import cz.rodro.dto.InstitutionDTO;
import cz.rodro.dto.mapper.InstitutionMapper;
import cz.rodro.entity.InstitutionEntity;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.repository.InstitutionRepository;
import cz.rodro.entity.repository.LocationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InstitutionServiceImpl implements InstitutionService {

    @Autowired
    private InstitutionRepository institutionRepository;

    @Autowired
    private InstitutionMapper institutionMapper;

    @Autowired
    private LocationRepository locationRepository;

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
    public InstitutionDTO createInstitution(InstitutionDTO dto) {
        if (dto.getInstitutionLocation() == null || dto.getInstitutionLocation().getId() == null) {
            throw new IllegalArgumentException("Institution location ID must be provided");
        }

        LocationEntity location = locationRepository.findById(dto.getInstitutionLocation().getId())
                .orElseThrow(() -> new EntityNotFoundException("Location not found with id: " + dto.getInstitutionLocation().getId()));

        InstitutionEntity entity = institutionMapper.toEntity(dto);
        entity.setInstitutionLocation(location);

        InstitutionEntity saved = institutionRepository.save(entity);
        return institutionMapper.toDto(saved);
    }

    @Override
    public InstitutionDTO updateInstitution(Long id, InstitutionDTO dto) {
        InstitutionEntity existing = institutionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Institution not found with id: " + id));

        existing.setInstitutionName(dto.getInstitutionName());
        existing.setInstitutionDescription(dto.getInstitutionDescription());

        if (dto.getInstitutionLocation() != null && dto.getInstitutionLocation().getId() != null) {
            LocationEntity location = locationRepository.findById(dto.getInstitutionLocation().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Location not found with id: " + dto.getInstitutionLocation().getId()));
            existing.setInstitutionLocation(location);
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
