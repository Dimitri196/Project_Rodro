package cz.rodro.service;

import cz.rodro.dto.PersonMilitaryServiceDTO;
import cz.rodro.dto.mapper.PersonMilitaryServiceMapper;
import cz.rodro.entity.MilitaryRankEntity;
import cz.rodro.entity.MilitaryStructureEntity;
import cz.rodro.entity.PersonEntity;
import cz.rodro.entity.PersonMilitaryServiceEntity;
import cz.rodro.entity.repository.MilitaryRankRepository;
import cz.rodro.entity.repository.MilitaryStructureRepository;
import cz.rodro.entity.repository.PersonMilitaryServiceRepository;
import cz.rodro.entity.repository.PersonRepository;
import cz.rodro.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete implementation of the PersonMilitaryService interface.
 * Manages the service records of persons, ensuring correct linking to Rank and Structure entities.
 */
@Service
@RequiredArgsConstructor
public class PersonMilitaryServiceImpl implements PersonMilitaryService {

    private final PersonMilitaryServiceRepository personMilitaryServiceRepository;
    private final PersonMilitaryServiceMapper personMilitaryServiceMapper;

    // Repositories needed to fetch and set foreign key entities
    private final PersonRepository personRepository;
    private final MilitaryStructureRepository militaryStructureRepository;
    private final MilitaryRankRepository militaryRankRepository;

    /**
     * @inheritDoc
     */
    @Override
    public List<PersonMilitaryServiceDTO> getAll() {
        return personMilitaryServiceRepository
                .findAll()
                .stream()
                .map(personMilitaryServiceMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * @inheritDoc
     */
    @Override
    public PersonMilitaryServiceDTO getPersonMilitaryService(Long serviceId) {
        // FIX: Use the specialized query to prevent LazyInitializationException when mapping
        PersonMilitaryServiceEntity entity = personMilitaryServiceRepository.findByIdWithDetails(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Military Service Record", "ID", serviceId));
        return personMilitaryServiceMapper.toDto(entity);
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public PersonMilitaryServiceDTO addPersonMilitaryService(PersonMilitaryServiceDTO dto) {
        // 1. Convert DTO to Entity (mapper ignores foreign key entities)
        PersonMilitaryServiceEntity entity = personMilitaryServiceMapper.toEntity(dto);

        // 2. Set Foreign Keys based on IDs from DTO
        setForeignKeys(entity, dto);

        // 3. Save and return DTO
        entity = personMilitaryServiceRepository.save(entity);
        return personMilitaryServiceMapper.toDto(entity);
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public void removePersonMilitaryService(Long serviceId) {
        // FIX: Use ResourceNotFoundException and check existence
        if (!personMilitaryServiceRepository.existsById(serviceId)) {
            throw new ResourceNotFoundException("Military Service Record", "ID", serviceId);
        }
        personMilitaryServiceRepository.deleteById(serviceId);
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public PersonMilitaryServiceDTO updatePersonMilitaryService(Long serviceId, PersonMilitaryServiceDTO dto) {
        PersonMilitaryServiceEntity entity = personMilitaryServiceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Military Service Record", "ID", serviceId));

        // 1. Update fields
        personMilitaryServiceMapper.updatePersonMilitaryServiceEntity(dto, entity);

        // 2. Update Foreign Keys if provided in DTO
        setForeignKeys(entity, dto);

        personMilitaryServiceRepository.save(entity);
        // FIX: Use findByIdWithDetails logic here to ensure correct DTO population after save
        return getPersonMilitaryService(entity.getId());
    }

    /**
     * @inheritDoc
     */
    @Override
    public PersonMilitaryServiceEntity fetchPersonMilitaryServiceById(Long id, String type) {
        return personMilitaryServiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(type, "ID", id));
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<PersonMilitaryServiceDTO> getByPersonId(Long personId) {
        // NOTE: This call will result in lazy loading issues if not handled by the mapper or a dedicated query.
        // For production use, consider adding a findByPersonIdWithDetails query to the repository.
        return personMilitaryServiceRepository.findByPersonId(personId)
                .stream()
                .map(personMilitaryServiceMapper::toDto)
                .collect(Collectors.toList());
    }

    // --- Private Helper Method to set Foreign Key Entities ---

    private void setForeignKeys(PersonMilitaryServiceEntity entity, PersonMilitaryServiceDTO dto) {
        // Set PersonEntity
        if (dto.getPersonId() != null) {
            PersonEntity personEntity = personRepository.findById(dto.getPersonId())
                    .orElseThrow(() -> new ResourceNotFoundException("Person", "ID", dto.getPersonId()));
            entity.setPerson(personEntity);
        }

        // Set MilitaryStructureEntity
        if (dto.getMilitaryStructure() != null && dto.getMilitaryStructure().getId() != null) {
            Long structureId = dto.getMilitaryStructure().getId();
            MilitaryStructureEntity structureEntity = militaryStructureRepository.findById(structureId)
                    .orElseThrow(() -> new ResourceNotFoundException("Military Structure", "ID", structureId));
            entity.setMilitaryStructure(structureEntity);
        } else {
            entity.setMilitaryStructure(null);
        }

        // Set MilitaryRankEntity
        if (dto.getMilitaryRank() != null && dto.getMilitaryRank().getId() != null) {
            Long rankId = dto.getMilitaryRank().getId();
            MilitaryRankEntity rankEntity = militaryRankRepository.findById(rankId)
                    .orElseThrow(() -> new ResourceNotFoundException("Military Rank", "ID", rankId));
            entity.setMilitaryRank(rankEntity);
        } else {
            entity.setMilitaryRank(null);
        }
    }
}
