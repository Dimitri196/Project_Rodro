package cz.rodro.service;

import cz.rodro.dto.MilitaryRankDTO;
import cz.rodro.dto.MilitaryStructureDTO;
import cz.rodro.dto.mapper.MilitaryRankMapper;
import cz.rodro.dto.mapper.MilitaryStructureMapper;
import cz.rodro.entity.MilitaryOrganizationEntity;
import cz.rodro.entity.MilitaryRankEntity;
import cz.rodro.entity.MilitaryStructureEntity;
import cz.rodro.entity.repository.MilitaryOrganizationRepository;
import cz.rodro.entity.repository.MilitaryRankRepository;
import cz.rodro.entity.repository.MilitaryStructureRepository;
import cz.rodro.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete implementation of the MilitaryStructureService interface.
 * Handles business logic, including linking to the parent organization and rank lookups.
 */
@Service
@RequiredArgsConstructor
public class MilitaryStructureServiceImpl implements MilitaryStructureService {

    private final MilitaryStructureRepository militaryStructureRepository;
    private final MilitaryStructureMapper militaryStructureMapper;
    private final MilitaryRankMapper militaryRankMapper;
    private final MilitaryRankRepository militaryRankRepository;
    private final MilitaryOrganizationRepository militaryOrganizationRepository;

    /**
     * @inheritDoc
     */
    @Override
    public List<MilitaryStructureDTO> getAll() {
        return militaryStructureRepository
                .findAll()
                .stream()
                .map(militaryStructureMapper::toMilitaryStructureDTO)
                .collect(Collectors.toList());
    }

    /**
     * @inheritDoc
     */
    @Override
    public MilitaryStructureDTO getMilitaryStructure(Long structureId) {
        // FIX: Find by ID, as findByIdWithParent relies on removed hierarchy field
        MilitaryStructureEntity entity = militaryStructureRepository.findById(structureId)
                .orElseThrow(() -> new ResourceNotFoundException("Military Structure", "ID", structureId));
        return militaryStructureMapper.toMilitaryStructureDTO(entity);
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public MilitaryStructureDTO addMilitaryStructure(MilitaryStructureDTO dto) {
        MilitaryStructureEntity entity = militaryStructureMapper.toMilitaryStructureEntity(dto);

        // Logic to set the MilitaryOrganizationEntity from the DTO
        setOrganization(entity, dto.getOrganizationId());

        // REMOVED: Logic to set the parent structure (Hierarchy is removed)

        entity = militaryStructureRepository.save(entity);
        return militaryStructureMapper.toMilitaryStructureDTO(entity);
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public void deleteMilitaryStructure(Long structureId) {
        // FIX: Changed return type to void and used ResourceNotFoundException
        if (!militaryStructureRepository.existsById(structureId)) {
            throw new ResourceNotFoundException("Military Structure", "ID", structureId);
        }
        militaryStructureRepository.deleteById(structureId);
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public MilitaryStructureDTO updateMilitaryStructure(Long structureId, MilitaryStructureDTO dto) {
        MilitaryStructureEntity entity = militaryStructureRepository.findById(structureId)
                .orElseThrow(() -> new ResourceNotFoundException("Military Structure", "ID", structureId));

        // Logic to update the MilitaryOrganizationEntity from the DTO
        if (dto.getOrganizationId() != null) {
            setOrganization(entity, dto.getOrganizationId());
        }

        // REMOVED: Logic to update the parent structure

        militaryStructureMapper.updateMilitaryStructureEntity(dto, entity);
        militaryStructureRepository.save(entity);
        return militaryStructureMapper.toMilitaryStructureDTO(entity);
    }

    /**
     * @inheritDoc
     */
    @Override
    public MilitaryStructureEntity fetchMilitaryStructureById(Long id, String type) {
        return militaryStructureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(type, "ID", id));
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<MilitaryRankDTO> getRanksForStructure(Long structureId) {
        // 1. Ensure the structure exists before querying for ranks
        if (!militaryStructureRepository.existsById(structureId)) {
            throw new ResourceNotFoundException("Military Structure", "ID", structureId);
        }

        // 2. Find ranks associated directly with the military structure.
        List<MilitaryRankEntity> ranks = militaryRankRepository.findByMilitaryStructureId(structureId);

        return ranks.stream()
                .map(militaryRankMapper::toMilitaryRankDTO)
                .collect(Collectors.toList());
    }

    // --- Private Helper Method ---

    private void setOrganization(MilitaryStructureEntity entity, Long organizationId) {
        if (organizationId != null) {
            MilitaryOrganizationEntity organizationEntity = militaryOrganizationRepository.findById(organizationId)
                    .orElseThrow(() -> new ResourceNotFoundException("Military Organization", "ID", organizationId));
            entity.setOrganization(organizationEntity);
        } else {
            // You might want to allow null organization or enforce it based on business rules
            entity.setOrganization(null);
        }
    }

}
