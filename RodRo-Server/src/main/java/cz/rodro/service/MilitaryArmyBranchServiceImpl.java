package cz.rodro.service;

import cz.rodro.dto.MilitaryArmyBranchDTO;
import cz.rodro.dto.MilitaryRankDTO;
import cz.rodro.dto.mapper.MilitaryArmyBranchMapper;
import cz.rodro.dto.mapper.MilitaryRankMapper;
import cz.rodro.entity.MilitaryArmyBranchEntity;
import cz.rodro.entity.repository.MilitaryArmyBranchRepository;
import cz.rodro.entity.repository.MilitaryRankRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete implementation of the MilitaryArmyBranchService interface.
 * Handles business logic and coordinates data flow between repositories and mappers.
 */
@Service
@RequiredArgsConstructor
public class MilitaryArmyBranchServiceImpl implements MilitaryArmyBranchService {

    private final MilitaryArmyBranchRepository branchRepository;
    private final MilitaryArmyBranchMapper branchMapper;
    private final MilitaryRankRepository rankRepository;
    private final MilitaryRankMapper rankMapper;

    /**
     * @inheritDoc
     */
    @Override
    public List<MilitaryArmyBranchDTO> findAll() {
        return branchRepository.findAll()
                .stream()
                .map(branchMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * @inheritDoc
     */
    @Override
    public MilitaryArmyBranchDTO findById(Long id) {
        MilitaryArmyBranchEntity entity = branchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Army branch not found with ID: " + id));
        return branchMapper.toDto(entity);
    }

    /**
     * @inheritDoc
     */
    @Override
    public MilitaryArmyBranchDTO create(MilitaryArmyBranchDTO dto) {
        MilitaryArmyBranchEntity entity = branchMapper.toEntity(dto);
        // Note: You might want to check for name uniqueness before saving
        return branchMapper.toDto(branchRepository.save(entity));
    }

    /**
     * @inheritDoc
     */
    @Override
    public MilitaryArmyBranchDTO update(Long id, MilitaryArmyBranchDTO dto) {
        MilitaryArmyBranchEntity entity = branchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Army branch not found with ID: " + id));

        // FIX: Update the correct field 'name'
        entity.setName(dto.getName());

        // Alternatively, use an update mapper if available: branchMapper.updateMilitaryArmyBranchEntity(dto, entity);

        return branchMapper.toDto(branchRepository.save(entity));
    }

    /**
     * @inheritDoc
     */
    @Override
    public void delete(Long id) {
        if (!branchRepository.existsById(id)) {
            throw new EntityNotFoundException("Army branch not found with ID: " + id);
        }
        branchRepository.deleteById(id);
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<MilitaryRankDTO> getRanksByBranchId(Long branchId) {
        // FIX: Use the corrected repository method name for finding ranks via organization's branch ID
        return rankRepository.findByMilitaryOrganizationArmyBranchId(branchId)
                .stream()
                .map(rankMapper::toMilitaryRankDTO)
                .collect(Collectors.toList());
    }
}
