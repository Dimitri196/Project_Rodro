package cz.rodro.dto.mapper;

import cz.rodro.dto.MilitaryRankDTO;
import cz.rodro.entity.MilitaryRankEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {MilitaryOrganizationMapper.class, MilitaryStructureSimpleMapper.class})
@Component
public interface MilitaryRankMapper {

    @Mapping(source = "militaryOrganization", target = "militaryOrganization")
    @Mapping(source = "militaryStructure", target = "militaryStructureDTO")
    @Mapping(source = "rankImageUrl", target = "rankImageUrl")
    @Mapping(target = "persons", ignore = true)
    MilitaryRankDTO toMilitaryRankDTO(MilitaryRankEntity entity);

    @Mapping(source = "militaryOrganization", target = "militaryOrganization")
    @Mapping(source = "militaryStructureDTO", target = "militaryStructure")
    @Mapping(source = "rankImageUrl", target = "rankImageUrl")
    MilitaryRankEntity toMilitaryRankEntity(MilitaryRankDTO dto);

    void updateMilitaryRankEntity(MilitaryRankDTO dto, @MappingTarget MilitaryRankEntity entity);

}
