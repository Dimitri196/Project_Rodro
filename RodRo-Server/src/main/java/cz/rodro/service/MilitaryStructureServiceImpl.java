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
import cz.rodro.exception.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class MilitaryStructureServiceImpl implements MilitaryStructureService {

    @Autowired
    private MilitaryStructureRepository militaryStructureRepository;

    @Autowired
    private MilitaryStructureMapper militaryStructureMapper;

    @Autowired
    private MilitaryRankMapper militaryRankMapper;

    @Autowired
    private MilitaryRankRepository militaryRankRepository;

    // New dependency for handling the organization relationship
    @Autowired
    private MilitaryOrganizationRepository militaryOrganizationRepository;


    @Override
    public List<MilitaryStructureDTO> getAll() {
        return militaryStructureRepository
                .findAll()
                .stream()
                .map(militaryStructureMapper::toMilitaryStructureDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MilitaryStructureDTO getMilitaryStructure(long structureId) {
        MilitaryStructureEntity entity = militaryStructureRepository.findByIdWithParent(structureId)
                .orElseThrow(() -> new NotFoundException("MilitaryStructure with id " + structureId + " wasn't found."));
        return militaryStructureMapper.toMilitaryStructureDTO(entity);
    }

    @Override
    @Transactional
    public MilitaryStructureDTO addMilitaryStructure(MilitaryStructureDTO dto) {
        MilitaryStructureEntity entity = militaryStructureMapper.toMilitaryStructureEntity(dto);

        // Logic to set the MilitaryOrganizationEntity from the DTO
        if (dto.getOrganization() != null && dto.getOrganization().getId() != null) {
            MilitaryOrganizationEntity organizationEntity = militaryOrganizationRepository.findById(dto.getOrganization().getId())
                    .orElseThrow(() -> new NotFoundException("MilitaryOrganization with id " + dto.getOrganization().getId() + " wasn't found."));
            entity.setOrganization(organizationEntity);
        } else {
            entity.setOrganization(null);
        }

        // Logic to set the parent structure from the DTO
        if (dto.getParentStructure() != null && dto.getParentStructure().getId() != null) {
            MilitaryStructureEntity parentEntity = militaryStructureRepository.findById(dto.getParentStructure().getId())
                    .orElseThrow(() -> new NotFoundException("Parent MilitaryStructure with id " + dto.getParentStructure().getId() + " wasn't found."));
            entity.setParentStructure(parentEntity);
        } else {
            entity.setParentStructure(null);
        }

        entity = militaryStructureRepository.save(entity);
        return militaryStructureMapper.toMilitaryStructureDTO(entity);
    }

    @Override
    public MilitaryStructureDTO removeMilitaryStructure(long structureId) {
        MilitaryStructureEntity entity = militaryStructureRepository.findById(structureId)
                .orElseThrow(EntityNotFoundException::new);
        MilitaryStructureDTO dto = militaryStructureMapper.toMilitaryStructureDTO(entity);
        militaryStructureRepository.delete(entity);
        return dto;
    }

    @Override
    @Transactional
    public MilitaryStructureDTO updateMilitaryStructure(Long structureId, MilitaryStructureDTO dto) {
        MilitaryStructureEntity entity = militaryStructureRepository.findById(structureId)
                .orElseThrow(() -> new NotFoundException("MilitaryStructure with id " + structureId + " wasn't found in the database"));

        // Logic to update the MilitaryOrganizationEntity from the DTO
        if (dto.getOrganization() != null && dto.getOrganization().getId() != null) {
            MilitaryOrganizationEntity organizationEntity = militaryOrganizationRepository.findById(dto.getOrganization().getId())
                    .orElseThrow(() -> new NotFoundException("MilitaryOrganization with id " + dto.getOrganization().getId() + " wasn't found."));
            entity.setOrganization(organizationEntity);
        } else {
            entity.setOrganization(null);
        }

        // Logic to update the parent structure from the DTO
        if (dto.getParentStructure() != null && dto.getParentStructure().getId() != null) {
            MilitaryStructureEntity parentEntity = militaryStructureRepository.findById(dto.getParentStructure().getId())
                    .orElseThrow(() -> new NotFoundException("Parent MilitaryStructure with id " + dto.getParentStructure().getId() + " wasn't found."));
            entity.setParentStructure(parentEntity);
        } else {
            entity.setParentStructure(null);
        }

        militaryStructureMapper.updateMilitaryStructureEntity(dto, entity);
        militaryStructureRepository.save(entity);
        return militaryStructureMapper.toMilitaryStructureDTO(entity);
    }

    @Override
    public MilitaryStructureEntity fetchMilitaryStructureById(Long id, String type) {
        return militaryStructureRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(type + " with id " + id + " wasn't found in the database."));
    }

    @Override
    public List<MilitaryRankDTO> getRanksForStructure(Long structureId) {
        // Find ranks associated directly with the military structure using the new repository method.
        List<MilitaryRankEntity> ranks = militaryRankRepository.findByMilitaryStructureId(structureId);

        return ranks.stream()
                .map(militaryRankMapper::toMilitaryRankDTO)
                .collect(Collectors.toList());
    }

    /**
     * Corrected method to get all sub-structures (e.g., regiments) for a given parent structure,
     * with each sub-structure containing its specific ranks.
     * @param parentStructureId The ID of the parent military structure.
     * @return A list of MilitaryStructureDTO objects, each with its specific ranks populated.
     */
    @Override
    public List<MilitaryStructureDTO> getRegimentsWithRanksForStructure(Long parentStructureId) {
        // Step 1: Find all sub-structures (regiments) of the parent structure.
        List<MilitaryStructureEntity> regiments = militaryStructureRepository.findByParentStructure_Id(parentStructureId);

        // Step 2: For each regiment, get the ranks associated directly with it and populate the DTO.
        return regiments.stream()
                .map(regiment -> {
                    MilitaryStructureDTO regimentDto = militaryStructureMapper.toMilitaryStructureDTO(regiment);

                    // We now get ranks directly from the MilitaryRankRepository using the new method
                    List<MilitaryRankEntity> ranks = militaryRankRepository.findByMilitaryStructureId(regiment.getId());

                    List<MilitaryRankDTO> rankDtos = ranks.stream()
                            .map(militaryRankMapper::toMilitaryRankDTO)
                            .collect(Collectors.toList());

                    regimentDto.setRanks(rankDtos);

                    return regimentDto;
                })
                .collect(Collectors.toList());
    }
}
