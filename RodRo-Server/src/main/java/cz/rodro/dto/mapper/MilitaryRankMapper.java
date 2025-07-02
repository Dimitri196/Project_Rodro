package cz.rodro.dto.mapper;

import cz.rodro.dto.MilitaryRankDTO;
import cz.rodro.entity.MilitaryRankEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {MilitaryStructureMapper.class})
public interface MilitaryRankMapper {

    @Mapping(source = "armyBranch.armyBranchName", target = "armyBranchName")
    @Mapping(target = "persons", ignore = true) // avoid the cycle here
    MilitaryRankDTO toMilitaryRankDTO(MilitaryRankEntity entity);

    MilitaryRankEntity toMilitaryRankEntity(MilitaryRankDTO dto);

    void updateMilitaryRankEntity(MilitaryRankDTO dto, @MappingTarget MilitaryRankEntity entity);
}