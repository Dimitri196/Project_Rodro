package cz.rodro.service;

import cz.rodro.dto.LocationDTO;
import cz.rodro.dto.SubdivisionDTO;
import cz.rodro.dto.mapper.DistrictMapper;
import cz.rodro.dto.mapper.LocationHistoryMapper;
import cz.rodro.dto.mapper.LocationMapper;
import cz.rodro.dto.mapper.SubdivisionMapper;
import cz.rodro.entity.DistrictEntity;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.SubdivisionEntity;
import cz.rodro.entity.repository.DistrictRepository;
import cz.rodro.entity.repository.LocationHistoryRepository;
import cz.rodro.entity.repository.SubdivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cz.rodro.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubdivisionServiceImpl implements SubdivisionService {

    @Autowired
    private SubdivisionRepository subdivisionRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private SubdivisionMapper subdivisionMapper;

    @Autowired
    private DistrictMapper districtMapper;

    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private LocationHistoryMapper locationHistoryMapper;

    @Autowired
    private LocationHistoryRepository locationHistoryRepository;


//    @Override
//    public List<SubdivisionDTO> getAll() {
//        return subdivisionRepository.findAll().stream()
//                .map(subdivisionMapper::toSubdivisionDTO)
//                .collect(Collectors.toList());
//    }

    @Override
    public List<SubdivisionDTO> getAll() {
        List<SubdivisionEntity> subdivisions = subdivisionRepository.findAll();

        return subdivisions.stream().map(subdivision -> {
            SubdivisionDTO dto = subdivisionMapper.toSubdivisionDTO(subdivision);

            // Fetch and attach locations
            List<LocationEntity> locationEntities = locationHistoryRepository.findDistinctLocationsBySubdivisionId(subdivision.getId());
            List<LocationDTO> locationDTOs = locationEntities.stream()
                    .map(locationMapper::toLocationDTO)
                    .collect(Collectors.toList());
            dto.setLocations(locationDTOs);

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public SubdivisionDTO getById(Long id) {
        SubdivisionEntity entity = subdivisionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subdivision not found"));

        SubdivisionDTO dto = subdivisionMapper.toSubdivisionDTO(entity);

        // 👉 Fetch locations via LocationHistory
        List<LocationEntity> locationEntities = locationHistoryRepository.findDistinctLocationsBySubdivisionId(id);
        List<LocationDTO> locationDTOs = locationEntities.stream()
                .map(locationMapper::toLocationDTO)
                .collect(Collectors.toList());

        dto.setLocations(locationDTOs); // 👈 Set them in the DTO

        return dto;
    }

    @Override
    public SubdivisionDTO create(SubdivisionDTO dto) {
        // District lookup and validation
        if (dto.getDistrict() == null || dto.getDistrict().getId() == null) {
            throw new NotFoundException("District not provided for Subdivision creation");
        }

        DistrictEntity district = districtRepository.findById(dto.getDistrict().getId())
                .orElseThrow(() -> new NotFoundException("District not found"));

        // Convert DTO to Entity
        SubdivisionEntity entity = subdivisionMapper.toSubdivisionEntity(dto);
        entity.setDistrict(district);

        // Save the entity
        SubdivisionEntity savedEntity = subdivisionRepository.save(entity);
        return subdivisionMapper.toSubdivisionDTO(savedEntity);
    }

    @Override
    public SubdivisionDTO update(Long id, SubdivisionDTO dto) {
        SubdivisionEntity existingEntity = subdivisionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subdivision not found"));

        // If district is provided in the DTO, update it
        if (dto.getDistrict() != null && dto.getDistrict().getId() != null) {
            DistrictEntity district = districtRepository.findById(dto.getDistrict().getId())
                    .orElseThrow(() -> new NotFoundException("District not found"));
            existingEntity.setDistrict(district);
        }

        // Update the remaining fields in the entity
        subdivisionMapper.updateSubdivisionEntity(dto, existingEntity);

        // Save and return the updated entity
        SubdivisionEntity updatedEntity = subdivisionRepository.save(existingEntity);
        return subdivisionMapper.toSubdivisionDTO(updatedEntity);
    }

    @Override
    public void delete(Long id) {
        // Check if the subdivision exists before deleting
        if (!subdivisionRepository.existsById(id)) {
            throw new NotFoundException("Subdivision not found");
        }
        subdivisionRepository.deleteById(id);
    }
}

