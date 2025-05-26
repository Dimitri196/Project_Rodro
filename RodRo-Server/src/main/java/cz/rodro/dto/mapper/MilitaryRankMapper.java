package cz.rodro.dto.mapper;

import cz.rodro.dto.MilitaryRankDTO;
import cz.rodro.entity.MilitaryRankEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = { MilitaryOrganizationMapper.class })
public interface MilitaryRankMapper {

    MilitaryRankDTO toMilitaryRankDTO(MilitaryRankEntity entity);

    MilitaryRankEntity toMilitaryRankEntity(MilitaryRankDTO dto);

    void updateMilitaryRankEntity(MilitaryRankDTO dto, @MappingTarget MilitaryRankEntity entity);
}