package cz.rodro.dto.mapper;

import cz.rodro.dto.MilitaryStructureDTO;
import cz.rodro.entity.MilitaryStructureEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {MilitaryArmyBranchMapper.class})
public interface MilitaryStructureMapper {

    @Mapping(target = "organization.structures", ignore = true) // Break circular reference
    @Mapping(target = "organization.country.provinces", ignore = true)
    @Mapping(source = "armyBranch.armyBranchName", target = "armyBranchName")
    MilitaryStructureDTO toMilitaryStructureDTO(MilitaryStructureEntity entity);

    @Mapping(target = "organization.structures", ignore = true)
    MilitaryStructureEntity toMilitaryStructureEntity(MilitaryStructureDTO dto);

    void updateMilitaryStructureEntity(MilitaryStructureDTO dto, @MappingTarget MilitaryStructureEntity entity);
}