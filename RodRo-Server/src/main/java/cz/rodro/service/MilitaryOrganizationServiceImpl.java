package cz.rodro.service;

import cz.rodro.dto.MilitaryOrganizationDTO;
import cz.rodro.dto.mapper.MilitaryOrganizationMapper;
import cz.rodro.entity.CountryEntity;
import cz.rodro.entity.MilitaryArmyBranchEntity;
import cz.rodro.entity.MilitaryOrganizationEntity;
import cz.rodro.entity.repository.CountryRepository;
import cz.rodro.entity.repository.MilitaryArmyBranchRepository;
import cz.rodro.entity.repository.MilitaryOrganizationRepository;
import cz.rodro.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MilitaryOrganizationServiceImpl implements MilitaryOrganizationService {

    private final MilitaryOrganizationRepository militaryOrganizationRepository;
    private final MilitaryOrganizationMapper militaryOrganizationMapper;

    // Repositories needed to fetch and set foreign key entities
    private final CountryRepository countryRepository;
    private final MilitaryArmyBranchRepository armyBranchRepository;

    // --- CRUD Operations ---

    /**
     * @inheritDoc
     */
    @Override
    public List<MilitaryOrganizationDTO> getAll() {
        return militaryOrganizationRepository
                .findAll()
                .stream()
                .map(militaryOrganizationMapper::toMilitaryOrganizationDTO)
                .collect(Collectors.toList());
    }

    /**
     * @inheritDoc
     */
    @Override
    public MilitaryOrganizationDTO getMilitaryOrganization(Long organizationId) {
        MilitaryOrganizationEntity entity = militaryOrganizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Military Organization", "ID", organizationId));
        return militaryOrganizationMapper.toMilitaryOrganizationDTO(entity);
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public MilitaryOrganizationDTO addMilitaryOrganization(MilitaryOrganizationDTO dto) {
        // 1. Convert DTO to Entity (mapper ignores foreign key entities)
        MilitaryOrganizationEntity entity = militaryOrganizationMapper.toMilitaryOrganizationEntity(dto);

        // 2. Set Foreign Keys based on IDs from DTO
        // NOTE: Uses ArmyBranch's ID from the MilitaryArmyBranchSimpleDTO embedded in MilitaryOrganizationDTO
        Long branchId = dto.getArmyBranch() != null ? dto.getArmyBranch().getId() : null;
        setForeignKeys(entity, dto.getCountryId(), branchId);

        // 3. Save and return DTO
        entity = militaryOrganizationRepository.save(entity);
        return militaryOrganizationMapper.toMilitaryOrganizationDTO(entity);
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public void deleteMilitaryOrganization(Long organizationId) {
        if (!militaryOrganizationRepository.existsById(organizationId)) {
            throw new ResourceNotFoundException("Military Organization", "ID", organizationId);
        }
        militaryOrganizationRepository.deleteById(organizationId);
    }

    /**
     * @inheritDoc
     */
    @Override
    @Transactional
    public MilitaryOrganizationDTO updateMilitaryOrganization(Long organizationId, MilitaryOrganizationDTO dto) {
        MilitaryOrganizationEntity entity = militaryOrganizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Military Organization", "ID", organizationId));

        // 1. Update fields using MapStruct mapper
        militaryOrganizationMapper.updateMilitaryOrganizationEntity(dto, entity);

        // 2. Conditionally update Foreign Keys if IDs are provided in DTO
        Long branchId = dto.getArmyBranch() != null ? dto.getArmyBranch().getId() : null;
        if (dto.getCountryId() != null || branchId != null) {
            setForeignKeys(entity, dto.getCountryId(), branchId);
        }

        militaryOrganizationRepository.save(entity);
        return militaryOrganizationMapper.toMilitaryOrganizationDTO(entity);
    }

    /**
     * @inheritDoc
     */
    @Override
    public MilitaryOrganizationEntity fetchMilitaryOrganizationById(Long id, String type) {
        // Uses the 'type' parameter to construct the error message.
        return militaryOrganizationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(type, "ID", id));
    }

    // --- Private Helper Method to set Foreign Key Entities ---

    private void setForeignKeys(MilitaryOrganizationEntity entity, Long countryId, Long branchId) {
        // Set Country Entity
        if (countryId != null) {
            CountryEntity countryEntity = countryRepository.findById(countryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Country", "ID", countryId));
            entity.setCountry(countryEntity);
        }

        // Set Army Branch Entity
        if (branchId != null) {
            MilitaryArmyBranchEntity branchEntity = armyBranchRepository.findById(branchId)
                    .orElseThrow(() -> new ResourceNotFoundException("Army Branch", "ID", branchId));
            entity.setArmyBranch(branchEntity);
        }
    }
}