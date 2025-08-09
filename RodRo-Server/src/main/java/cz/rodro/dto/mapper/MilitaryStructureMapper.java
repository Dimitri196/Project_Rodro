package cz.rodro.dto.mapper;

import cz.rodro.dto.MilitaryStructureDTO;
import cz.rodro.entity.MilitaryStructureEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {MilitaryOrganizationMapper.class, MilitaryArmyBranchMapper.class})
public interface MilitaryStructureMapper {

    @Mapping(target = "organization.structures", ignore = true)
    @Mapping(target = "organization.country.provinces", ignore = true)
    @Mapping(source = "bannerImageUrl", target = "bannerImageUrl")
    @Mapping(source = "organization.armyBranch.armyBranchName", target = "armyBranchName")
    @Mapping(source = "parentStructure", target = "parentStructure")
    @Mapping(source = "subStructures", target = "subStructures")
    MilitaryStructureDTO toMilitaryStructureDTO(MilitaryStructureEntity entity);


    @Mapping(target = "organization.structures", ignore = true)
    @Mapping(source = "bannerImageUrl", target = "bannerImageUrl")
    @Mapping(source = "parentStructure", target = "parentStructure")
    @Mapping(source = "subStructures", target = "subStructures")
    MilitaryStructureEntity toMilitaryStructureEntity(MilitaryStructureDTO dto);

    void updateMilitaryStructureEntity(MilitaryStructureDTO dto, @MappingTarget MilitaryStructureEntity entity);
}
