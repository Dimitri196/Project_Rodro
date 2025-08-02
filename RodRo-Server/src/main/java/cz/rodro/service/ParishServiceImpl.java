package cz.rodro.service;

import cz.rodro.dto.ParishDTO;
import cz.rodro.dto.ParishLocationDTO;
import cz.rodro.dto.mapper.ParishLocationMapper;
import cz.rodro.dto.mapper.ParishMapper;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.ParishEntity;
import cz.rodro.entity.ParishLocationEntity;
import cz.rodro.entity.repository.ParishLocationRepository;
import cz.rodro.entity.repository.ParishRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cz.rodro.exception.NotFoundException;

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
    private LocationService locationService;

    // You'll also need a repository for the join table
    @Autowired
    private ParishLocationRepository parishLocationRepository;

    @Override
    public List<ParishDTO> getAllParishes() {
        return parishRepository
                .findAll()
                .stream()
                .map(parishMapper::toParishDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ParishDTO getParish(long parishId) {
        ParishEntity parishEntity = parishRepository
                .findById(parishId)
                .orElseThrow(() -> new EntityNotFoundException("Parish not found"));
        return parishMapper.toParishDTO(parishEntity);
    }

    @Override
    public ParishDTO addParish(ParishDTO parishDTO) {
        // Fetch the location entity by its ID
        LocationEntity parishLocation = locationService.fetchLocationById(parishDTO.getParishLocation().getId(), "Parish Location");

        // Map the DTO to the entity. Note: The location field is not mapped here.
        ParishEntity parishEntity = parishMapper.toParishEntity(parishDTO);

        // Save the new parish entity first to get an ID
        parishEntity = parishRepository.save(parishEntity);

        // Create the new join entity to link the parish and location
        ParishLocationEntity parishLocationEntity = new ParishLocationEntity();
        parishLocationEntity.setParish(parishEntity);
        parishLocationEntity.setLocation(parishLocation);
        parishLocationEntity.setParishName(parishDTO.getParishName());
        parishLocationEntity.setLocationName(parishLocation.getLocationName());

        // Save the join entity
        parishLocationRepository.save(parishLocationEntity);

        // Return the saved parish DTO
        return parishMapper.toParishDTO(parishEntity);
    }

    @Override
    public void removeParish(long parishId) {
        try {
            ParishEntity parish = fetchParishById(parishId);
            parishRepository.delete(parish);
        } catch (EntityNotFoundException ignored) {
            // Silently fail if parish is not found
        }
    }

    @Override
    @Transactional
    public ParishDTO updateParish(Long parishId, ParishDTO parishDTO) {
        // Fetch the existing parish
        ParishEntity existingParish = fetchParishById(parishId);

        // Map updated values from DTO to Entity
        parishMapper.updateParishEntity(parishDTO, existingParish);

        // Save updated entity
        ParishEntity savedParishEntity = parishRepository.save(existingParish);
        return parishMapper.toParishDTO(savedParishEntity);
    }


    @Override
    public List<ParishLocationDTO> getParishLocations(long parishId) {
        // Find all ParishLocationEntity records for the given parishId
        return parishLocationRepository.findByParishId(parishId)
                .stream()
                .map(parishLocationMapper::toDto)
                .collect(Collectors.toList());
    }

    private ParishEntity fetchParishById(long id) {
        return parishRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parish with id " + id + " wasn't found in the database."));
    }

    @Override
    public List<ParishLocationDTO> getParishesByLocationId(long locationId) {
        return parishLocationRepository.findByLocationId(locationId)
                .stream()
                .map(parishLocationMapper::toDto)
                .collect(Collectors.toList());
    }

}
