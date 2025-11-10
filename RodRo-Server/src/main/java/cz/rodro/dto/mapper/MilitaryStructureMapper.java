package cz.rodro.dto.mapper;

import cz.rodro.dto.MilitaryStructureDTO;
import cz.rodro.entity.MilitaryStructureEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper(
        componentModel = "spring")
@Component
public interface MilitaryStructureMapper {

    @Mapping(target = "unitName", source = "name")
    @Mapping(target = "unitType", source = "type")
    @Mapping(source = "organization.id", target = "organizationId")
    @Mapping(source = "organization.name", target = "organizationName")
    @Mapping(source = "organization.armyBranch.name", target = "armyBranchName")
    MilitaryStructureDTO toMilitaryStructureDTO(MilitaryStructureEntity entity);

    MilitaryStructureEntity toMilitaryStructureEntity(MilitaryStructureDTO dto);

    void updateMilitaryStructureEntity(MilitaryStructureDTO dto, @MappingTarget MilitaryStructureEntity entity);
}
