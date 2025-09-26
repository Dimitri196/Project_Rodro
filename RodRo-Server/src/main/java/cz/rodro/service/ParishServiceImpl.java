package cz.rodro.service;

import cz.rodro.dto.ParishDTO;
import cz.rodro.dto.ParishLocationDTO;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.ParishEntity;
import cz.rodro.entity.ParishLocationEntity;
import cz.rodro.entity.repository.ParishLocationRepository;
import cz.rodro.entity.repository.ParishRepository;
import cz.rodro.dto.mapper.LocationMapper;
import cz.rodro.dto.mapper.ParishLocationMapper;
import cz.rodro.dto.mapper.ParishMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParishServiceImpl implements ParishService {

    @Autowired
    private ParishRepository parishRepository;

    @Autowired
    private ParishMapper parishMapper;

    @Autowired
    private ParishLocationMapper parishLocationMapper;

    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private ParishLocationRepository parishLocationRepository;

    @Override
    public List<ParishDTO> getAllParishes() {
        return parishRepository.findAll().stream()
                .map(parish -> {
                    ParishDTO dto = parishMapper.toParishDTO(parish);
                    if (!parish.getLocations().isEmpty()) {
                        ParishLocationEntity firstLocation = parish.getLocations().get(0);
                        dto.setLocation(locationMapper.toLocationDTO(firstLocation.getLocation()));
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ParishDTO getParish(long parishId) {
        ParishEntity parishEntity = fetchParishById(parishId);
        ParishDTO dto = parishMapper.toParishDTO(parishEntity);

        List<ParishLocationDTO> associatedLocations = parishEntity.getLocations().stream()
                .map(pl -> {
                    ParishLocationDTO plDto = new ParishLocationDTO();
                    plDto.setId(pl.getId()); // parish_location row ID
                    plDto.setParishId(pl.getParish().getId());
                    plDto.setLocationId(pl.getLocation().getId()); // important
                    plDto.setParishName(pl.getParishName());
                    plDto.setLocationName(pl.getLocationName());
                    plDto.setMainChurchName(pl.getMainChurchName());
                    plDto.setMainLocation(pl.isMainLocation()); // boolean
                    return plDto;
                })
                .collect(Collectors.toList());

        // Set mainLocation in ParishDTO
        associatedLocations.stream()
                .filter(ParishLocationDTO::isMainLocation)
                .findFirst()
                .ifPresent(mainLoc -> dto.setLocation(
                        locationMapper.toLocationDTO(
                                parishEntity.getLocations().stream()
                                        .filter(pl -> pl.getId().equals(mainLoc.getId()))
                                        .findFirst()
                                        .get()
                                        .getLocation()
                        )
                ));

        dto.setLocations(associatedLocations);

        return dto;
    }


    @Override
    @Transactional
    public ParishDTO addParish(ParishDTO parishDTO) {
        // Map DTO to entity
        ParishEntity parishEntity = parishMapper.toParishEntity(parishDTO);
        parishEntity = parishRepository.save(parishEntity);

        // Link to location if provided
        if (parishDTO.getLocation() != null) {
            LocationEntity locationEntity = parishMapperLocationHelper(parishDTO);
            ParishLocationEntity parishLocationEntity = new ParishLocationEntity();
            parishLocationEntity.setParish(parishEntity);
            parishLocationEntity.setLocation(locationEntity);
            parishLocationEntity.setParishName(parishDTO.getName());
            parishLocationEntity.setLocationName(locationEntity.getLocationName());
            parishLocationRepository.save(parishLocationEntity);

            // Populate DTO with location
            parishDTO.setLocation(locationMapper.toLocationDTO(locationEntity));
        }

        return parishMapper.toParishDTO(parishEntity);
    }

    @Override
    @Transactional
    public ParishDTO updateParish(Long parishId, ParishDTO parishDTO) {
        ParishEntity existingParish = fetchParishById(parishId);
        parishMapper.updateParishEntity(parishDTO, existingParish);
        ParishEntity savedParish = parishRepository.save(existingParish);

        // Update location association if provided
        if (parishDTO.getLocation() != null) {
            // Check if a ParishLocation exists
            List<ParishLocationEntity> existingLocations = existingParish.getLocations();
            ParishLocationEntity parishLocationEntity;
            if (existingLocations.isEmpty()) {
                parishLocationEntity = new ParishLocationEntity();
                parishLocationEntity.setParish(existingParish);
            } else {
                parishLocationEntity = existingLocations.get(0);
            }
            parishLocationEntity.setLocation(parishMapperLocationHelper(parishDTO));
            parishLocationEntity.setParishName(savedParish.getName());
            parishLocationEntity.setLocationName(parishDTO.getLocation().getLocationName());
            parishLocationRepository.save(parishLocationEntity);
        }

        ParishDTO resultDTO = parishMapper.toParishDTO(savedParish);
        if (!savedParish.getLocations().isEmpty()) {
            resultDTO.setLocation(locationMapper.toLocationDTO(savedParish.getLocations().get(0).getLocation()));
        }
        return resultDTO;
    }

    @Override
    public void removeParish(long parishId) {
        try {
            ParishEntity parish = fetchParishById(parishId);
            parishRepository.delete(parish);
        } catch (EntityNotFoundException ignored) {
        }
    }

    @Override
    public List<ParishLocationDTO> getParishLocations(long parishId) {
        return parishLocationRepository.findByParishId(parishId).stream()
                .map(parishLocationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ParishLocationDTO> getParishesByLocationId(long locationId) {
        return parishLocationRepository.findByLocationId(locationId).stream()
                .map(parishLocationMapper::toDto)
                .collect(Collectors.toList());
    }

    private ParishEntity fetchParishById(long id) {
        return parishRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parish with id " + id + " wasn't found in the database."));
    }

    /**
     * Helper method to fetch LocationEntity for a given ParishDTO
     */
    private LocationEntity parishMapperLocationHelper(ParishDTO parishDTO) {
        if (parishDTO.getLocation() == null || parishDTO.getLocation().getId() == null) {
            throw new IllegalArgumentException("Parish location must be provided with a valid id.");
        }
        return locationMapper.toLocationEntity(parishDTO.getLocation());
    }
}
