package cz.rodro.service;

import cz.rodro.dto.MilitaryRankDTO;
import cz.rodro.dto.PersonMilitaryServiceDTO;
import cz.rodro.dto.mapper.MilitaryRankMapper;
import cz.rodro.dto.mapper.PersonMilitaryServiceMapper;
import cz.rodro.entity.MilitaryOrganizationEntity;
import cz.rodro.entity.MilitaryRankEntity;
import cz.rodro.entity.MilitaryStructureEntity;
import cz.rodro.entity.repository.MilitaryOrganizationRepository;
import cz.rodro.entity.repository.MilitaryRankRepository;
import cz.rodro.entity.repository.MilitaryStructureRepository;
import cz.rodro.entity.repository.PersonMilitaryServiceRepository;
import cz.rodro.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete implementation of the MilitaryRankService interface.
 * Handles business logic, including linking to the organization/structure and fetching associated personnel.
 */
@Service
@RequiredArgsConstructor
public class MilitaryRankServiceImpl implements MilitaryRankService {

    // Using @RequiredArgsConstructor for constructor injection
    private final MilitaryRankRepository militaryRankRepository;
    private final MilitaryRankMapper militaryRankMapper;
    private final PersonMilitaryServiceRepository personMilitaryServiceRepository;
    private final PersonMilitaryServiceMapper personMilitaryServiceMapper;
    private final MilitaryOrganizationRepository militaryOrganizationRepository;
    private final MilitaryStructureRepository militaryStructureRepository;

    /**
     * @inheritDoc
     */
    @Override
    public List<MilitaryRankDTO> getAll() {
        return militaryRankRepository
                .findAll()
                .stream()
                .map(militaryRankMapper::toMilitaryRankDTO)
                .collect(Collectors.toList());
    }

    /**
     * @inheritDoc
     */
    @Override
    public MilitaryRankDTO getMilitaryRank(Long id) {
        MilitaryRankEntity entity = militaryRankRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Military Rank", "ID", id));
        return militaryRankMapper.toMilitaryRankDTO(entity);
    }

    /**
     * @inheritDoc
     */
    @Override
    public MilitaryRankDTO getMilitaryRankWithPersons(Long id) {
        // Use findByIdWithDetails if implemented, otherwise rely on the default findById and map.
        MilitaryRankEntity entity = militaryRankRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Military Rank", "ID", id));

        MilitaryRankDTO dto = militaryRankMapper.toMilitaryRankDTO(entity);

        // Fetch the list of associated service records
        List<PersonMilitaryServiceDTO> persons = personMilitaryServiceRepository.findAllByMilitaryRankId(id)
                .stream()
                .map(personMilitaryServiceMapper::toDto)
                .collect(Collectors.toList());

        dto.setPersons(persons);
        return dto;
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public MilitaryRankDTO addMilitaryRank(MilitaryRankDTO dto) {
        MilitaryRankEntity entity = militaryRankMapper.toMilitaryRankEntity(dto);

        // Helper method to set foreign keys
        setForeignKeys(entity, dto.getOrganizationId(), dto.getStructureId());

        entity = militaryRankRepository.save(entity);
        return militaryRankMapper.toMilitaryRankDTO(entity);
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public void deleteMilitaryRank(Long rankId) {
        // FIX: Changed return type to void and used ResourceNotFoundException
        if (!militaryRankRepository.existsById(rankId)) {
            throw new ResourceNotFoundException("Military Rank", "ID", rankId);
        }
        militaryRankRepository.deleteById(rankId);
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public MilitaryRankDTO updateMilitaryRank(Long rankId, MilitaryRankDTO dto) {
        MilitaryRankEntity entity = militaryRankRepository.findById(rankId)
                .orElseThrow(() -> new ResourceNotFoundException("Military Rank", "ID", rankId));

        // Update fields using MapStruct mapper
        militaryRankMapper.updateMilitaryRankEntity(dto, entity);

        // Conditionally update Foreign Keys if IDs are provided in DTO
        if (dto.getOrganizationId() != null || dto.getStructureId() != null) {
            setForeignKeys(entity, dto.getOrganizationId(), dto.getStructureId());
        }

        militaryRankRepository.save(entity);
        return militaryRankMapper.toMilitaryRankDTO(entity);
    }

    /**
     * @inheritDoc
     */
    @Override
    public MilitaryRankEntity fetchMilitaryRankById(Long id, String type) {
        return militaryRankRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(type, "ID", id));
    }

    // --- Private Helper Method to set Foreign Key Entities ---

    private void setForeignKeys(MilitaryRankEntity entity, Long organizationId, String structureIdStr) {
        // Set MilitaryOrganizationEntity
        if (organizationId != null) {
            MilitaryOrganizationEntity organizationEntity = militaryOrganizationRepository.findById(organizationId)
                    .orElseThrow(() -> new ResourceNotFoundException("Military Organization", "ID", organizationId));
            entity.setMilitaryOrganization(organizationEntity);
        } else {
            entity.setMilitaryOrganization(null);
        }

        // Set MilitaryStructureEntity (Requires String conversion for ID from DTO)
        if (structureIdStr != null && !structureIdStr.isBlank()) {
            Long structureId;
            try {
                structureId = Long.parseLong(structureIdStr);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid structure ID format: " + structureIdStr);
            }

            MilitaryStructureEntity structureEntity = militaryStructureRepository.findById(structureId)
                    .orElseThrow(() -> new ResourceNotFoundException("Military Structure", "ID", structureId));
            entity.setMilitaryStructure(structureEntity);
        } else {
            entity.setMilitaryStructure(null);
        }
    }
}
