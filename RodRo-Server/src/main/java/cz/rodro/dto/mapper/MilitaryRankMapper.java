package cz.rodro.dto.mapper;

import cz.rodro.dto.MilitaryRankDTO;
import cz.rodro.entity.MilitaryRankEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MilitaryRankMapper {

    @Mapping(target = "organizationId", source = "militaryOrganization.id")
    @Mapping(target = "organizationName", source = "militaryOrganization.name")
    @Mapping(target = "structureId", source = "militaryStructure.id")
    @Mapping(target = "structureName", source = "militaryStructure.name")
    MilitaryRankDTO toMilitaryRankDTO(MilitaryRankEntity entity);

    @Mapping(target = "id", source = "id")
    MilitaryRankEntity toMilitaryRankEntity(MilitaryRankDTO dto);

    void updateMilitaryRankEntity(MilitaryRankDTO dto, @MappingTarget MilitaryRankEntity entity);
}
