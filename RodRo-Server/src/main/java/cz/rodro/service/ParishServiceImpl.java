package cz.rodro.service;

import cz.rodro.dto.ParishDTO;
import cz.rodro.dto.mapper.ParishMapper;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.ParishEntity;
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
    private LocationService locationService;

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
        return parishMapper.toParishDTO(parishEntity); // Mapping Entity to DTO
    }

    @Override
    public ParishDTO addParish(ParishDTO parishDTO) {
        // Fetch the parish location entity by its ID
        LocationEntity parishLocation = locationService.fetchLocationById(parishDTO.getParishLocation().getId(), "Parish Location");

        // Map the DTO to the entity
        ParishEntity parishEntity = parishMapper.toParishEntity(parishDTO);

        // Set the location to the parish entity
        parishEntity.setParishLocation(parishLocation);

        // Save the new parish entity
        parishEntity = parishRepository.save(parishEntity);

        // Return the saved parish DTO
        return parishMapper.toParishDTO(parishEntity);
    }

    @Override
    public void removeParish(long parishId) {
        // Fetch and delete parish by ID
        try {
            ParishEntity parish = fetchParishById(parishId);
            parishRepository.delete(parish);
        } catch (NotFoundException ignored) {
            // Silently fail if parish is not found
        }
    }

    @Override
    @Transactional
    public ParishDTO updateParish(Long parishId, ParishDTO parishDTO) {
        // Fetch the existing parish
        ParishEntity existingParish = fetchParishById(parishId);

        // Validate the location ID if necessary
        LocationEntity parishLocation = locationService.fetchLocationById(parishDTO.getParishLocation().getId(), "Parish Location");

        // Map updated values from DTO to Entity
        parishMapper.updateParishEntity(parishDTO, existingParish);
        existingParish.setParishLocation(parishLocation);

        // Save updated entity
        ParishEntity savedParishEntity = parishRepository.save(existingParish);
        return parishMapper.toParishDTO(savedParishEntity);
    }

    private ParishEntity fetchParishById(long id) {
        return parishRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Parish with id " + id + " wasn't found in the database."));
    }
}

