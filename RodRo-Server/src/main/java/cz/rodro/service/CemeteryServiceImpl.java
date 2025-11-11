package cz.rodro.service;

import cz.rodro.dto.CemeteryDTO;
import cz.rodro.dto.mapper.CemeteryMapper;
import cz.rodro.entity.CemeteryEntity;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.repository.CemeteryRepository;
import cz.rodro.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CemeteryServiceImpl implements CemeteryService {

    @Autowired
    private CemeteryRepository cemeteryRepository;

    @Autowired
    private CemeteryMapper cemeteryMapper;

    @Autowired
    private LocationService locationService;

    @Override
    public List<CemeteryDTO> getAllCemeteries() {
        return cemeteryRepository.findAll()
                .stream()
                .map(cemeteryMapper::toCemeteryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CemeteryDTO getCemeteryById(long cemeteryId) {
        CemeteryEntity cemeteryEntity = cemeteryRepository
                .findById(cemeteryId)
                .orElseThrow(() -> new EntityNotFoundException("Cemetery not found"));
        return cemeteryMapper.toCemeteryDTO(cemeteryEntity);
    }

    @Override
    public CemeteryDTO addCemetery(CemeteryDTO cemeteryDTO) {
        if (cemeteryDTO.getCemeteryLocation() == null || cemeteryDTO.getCemeteryLocation().getId() == null) {
            throw new IllegalArgumentException("Cemetery location must be provided and contain a valid ID.");
        }

        LocationEntity cemeteryLocation = locationService.fetchLocationById(
                cemeteryDTO.getCemeteryLocation().getId(), "Cemetery Location"
        );

        CemeteryEntity cemeteryEntity = cemeteryMapper.toCemeteryEntity(cemeteryDTO);

        cemeteryEntity.setCemeteryLocation(cemeteryLocation);

        cemeteryEntity = cemeteryRepository.save(cemeteryEntity);

        return cemeteryMapper.toCemeteryDTO(cemeteryEntity);
    }

    @Override
    @Transactional
    public CemeteryDTO updateCemetery(Long cemeteryId, CemeteryDTO cemeteryDTO) {

        CemeteryEntity existingCemetery = fetchCemeteryById(cemeteryId);

        LocationEntity cemeteryLocation = locationService.fetchLocationById(cemeteryDTO.getCemeteryLocation().getId(), "Cemetery Location");

        cemeteryMapper.updateCemeteryEntity(cemeteryDTO, existingCemetery);
        existingCemetery.setCemeteryLocation(cemeteryLocation);

        existingCemetery = cemeteryRepository.save(existingCemetery);

        return cemeteryMapper.toCemeteryDTO(existingCemetery);
    }

    @Override
    public void deleteCemetery(Long cemeteryId) {
        CemeteryEntity cemeteryEntity = cemeteryRepository.findById(cemeteryId)
                .orElseThrow(() -> new RuntimeException("Cemetery not found"));

        cemeteryRepository.delete(cemeteryEntity);
        cemeteryMapper.toCemeteryDTO(cemeteryEntity);
    }

    private CemeteryEntity fetchCemeteryById(long id) {
        return cemeteryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cemetery with id " + id + " wasn't found in the database."));
    }

    @Override
    public List<CemeteryDTO> getCemeteriesByLocationId(Long locationId) {
        List<CemeteryEntity> cemeteries = cemeteryRepository.findByCemeteryLocation_Id(locationId);
        return cemeteries.stream()
                .map(cemeteryMapper::toCemeteryDTO)
                .collect(Collectors.toList());
    }


}
