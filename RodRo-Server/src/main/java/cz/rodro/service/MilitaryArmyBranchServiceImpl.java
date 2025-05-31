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

@Service
@RequiredArgsConstructor
public class MilitaryArmyBranchServiceImpl implements MilitaryArmyBranchService {

    private final MilitaryArmyBranchRepository branchRepository;
    private final MilitaryArmyBranchMapper branchMapper;
    private final MilitaryRankRepository rankRepository;
    private final MilitaryRankMapper rankMapper;

    @Override
    public List<MilitaryArmyBranchDTO> findAll() {
        return branchRepository.findAll()
                .stream()
                .map(branchMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MilitaryArmyBranchDTO findById(Long id) {
        MilitaryArmyBranchEntity entity = branchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Army branch not found"));
        return branchMapper.toDto(entity);
    }

    @Override
    public MilitaryArmyBranchDTO create(MilitaryArmyBranchDTO dto) {
        MilitaryArmyBranchEntity entity = branchMapper.toEntity(dto);
        return branchMapper.toDto(branchRepository.save(entity));
    }

    @Override
    public MilitaryArmyBranchDTO update(Long id, MilitaryArmyBranchDTO dto) {
        MilitaryArmyBranchEntity entity = branchRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Army branch not found"));
        entity.setArmyBranchName(dto.getArmyBranchName());
        return branchMapper.toDto(branchRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        branchRepository.deleteById(id);
    }

    @Override
    public List<MilitaryRankDTO> getRanksByBranchId(Long branchId) {
        return rankRepository.findByArmyBranch_Id(branchId)
                .stream()
                .map(rankMapper::toMilitaryRankDTO)
                .collect(Collectors.toList());
    }
}
