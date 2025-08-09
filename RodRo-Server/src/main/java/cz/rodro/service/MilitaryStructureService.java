package cz.rodro.service;

import cz.rodro.dto.MilitaryRankDTO;
import cz.rodro.dto.MilitaryStructureDTO;
import cz.rodro.entity.MilitaryStructureEntity;

import java.util.List;

public interface MilitaryStructureService {

    List<MilitaryStructureDTO> getAll();
    MilitaryStructureDTO getMilitaryStructure(long structureId);
    MilitaryStructureDTO addMilitaryStructure(MilitaryStructureDTO militaryStructureDTO);
    MilitaryStructureDTO removeMilitaryStructure(long structureId);
    MilitaryStructureDTO updateMilitaryStructure(Long structureId, MilitaryStructureDTO militaryStructureDTO);
    MilitaryStructureEntity fetchMilitaryStructureById(Long id, String type);


    // Add this method
    List<MilitaryRankDTO> getRanksForStructure(Long structureId);

    List<MilitaryStructureDTO> getRegimentsWithRanksForStructure(Long parentStructureId);

}
