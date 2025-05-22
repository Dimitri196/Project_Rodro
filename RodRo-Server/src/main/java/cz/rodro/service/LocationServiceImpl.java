package cz.rodro.service;

import cz.rodro.dto.LocationDTO;
import cz.rodro.dto.mapper.LocationMapper;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.repository.LocationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cz.rodro.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    LocationMapper locationMapper;

    @Override
    public List<LocationDTO> getAll() {
        return locationRepository
                .findAll()
                .stream()
                .map(i -> locationMapper.toLocationDTO(i))
                .collect(Collectors.toList());
    }

    @Override
    public LocationDTO getLocation(long locationId) {
        LocationEntity locationEntity = locationRepository.getReferenceById(locationId);
        return locationMapper.toLocationDTO(locationEntity);

    }

    @Override
    public LocationDTO addLocation(LocationDTO locationDTO) {
        LocationEntity locationEntity = locationMapper.toLocationEntity(locationDTO);
        locationEntity = locationRepository.save(locationEntity);
        return locationMapper.toLocationDTO(locationEntity);
    }

    @Override
    public LocationDTO removeLocation(long locationId) {
    LocationEntity locationEntity = locationRepository.findById(locationId).orElseThrow(EntityNotFoundException::new);
    LocationDTO model = locationMapper.toLocationDTO(locationEntity);
    locationRepository.delete(locationEntity);
    return model;
    }

    @Override
    @Transactional
    public LocationDTO updateLocation(Long locationId, LocationDTO locationDTO) {
        LocationEntity locationEntity = locationRepository.findById(locationId)
                .orElseThrow(() -> new NotFoundException("Location with id " + locationId + " wasn't found in the database"));

        // Update only primitive fields & relationships that don't involve cascades
        locationMapper.updateLocationEntity(locationDTO, locationEntity);
        locationRepository.save(locationEntity); // Save changes to base entity

        return locationMapper.toLocationDTO(locationEntity);
    }

    @Override
    public LocationEntity fetchLocationById(Long id, String type) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(type + " with id " + id + " wasn't found in the database."));
    }
}
