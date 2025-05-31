package cz.rodro.service;

import cz.rodro.dto.MilitaryArmyBranchDTO;
import cz.rodro.dto.MilitaryRankDTO;

import java.util.List;

public interface MilitaryArmyBranchService {

    List<MilitaryArmyBranchDTO> findAll();
    MilitaryArmyBranchDTO findById(Long id);
    MilitaryArmyBranchDTO create(MilitaryArmyBranchDTO dto);
    MilitaryArmyBranchDTO update(Long id, MilitaryArmyBranchDTO dto);
    void delete(Long id);
    List<MilitaryRankDTO> getRanksByBranchId(Long branchId);
}
