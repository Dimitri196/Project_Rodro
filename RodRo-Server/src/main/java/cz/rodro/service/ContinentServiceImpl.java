package cz.rodro.service;

import cz.rodro.dto.ContinentDTO;
import cz.rodro.dto.mapper.ContinentMapper;
import cz.rodro.entity.ContinentEntity;
import cz.rodro.entity.repository.ContinentRepository;
import cz.rodro.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContinentServiceImpl implements ContinentService {

    private final ContinentRepository continentRepository;
    private final ContinentMapper continentMapper;

    @Autowired
    public ContinentServiceImpl(ContinentRepository continentRepository, ContinentMapper continentMapper) {
        this.continentRepository = continentRepository;
        this.continentMapper = continentMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContinentDTO> getAllContinents() {
        return continentRepository.findAll().stream()
                .map(continentMapper::toContinentDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ContinentDTO getContinent(Long continentId) {
        ContinentEntity continentEntity = continentRepository.findById(continentId)
                .orElseThrow(() -> new ResourceNotFoundException("Continent", "id", continentId));
        return continentMapper.toContinentDTO(continentEntity);
    }

    @Override
    @Transactional
    public ContinentDTO addContinent(ContinentDTO continentDTO) {
        // Ensure ID is null for creation
        continentDTO.setId(null);

        ContinentEntity continentEntity = continentMapper.toContinentEntity(continentDTO);
        continentEntity = continentRepository.save(continentEntity);

        return continentMapper.toContinentDTO(continentEntity);
    }

    @Override
    @Transactional
    public ContinentDTO updateContinent(Long continentId, ContinentDTO continentDTO) {
        ContinentEntity existingEntity = continentRepository.findById(continentId)
                .orElseThrow(() -> new ResourceNotFoundException("Continent", "id", continentId));

        continentMapper.updateContinentEntityFromDTO(continentDTO, existingEntity);
        ContinentEntity updatedEntity = continentRepository.save(existingEntity);

        return continentMapper.toContinentDTO(updatedEntity);
    }

    @Override
    @Transactional
    public void deleteContinent(Long continentId) {
        if (!continentRepository.existsById(continentId)) {
            throw new ResourceNotFoundException("Continent", "id", continentId);
        }
        // Note: Deleting a continent will likely require cascading or manual removal of related
        // CountryContinentHistory records, depending on your database constraints.
        continentRepository.deleteById(continentId);
    }
}
