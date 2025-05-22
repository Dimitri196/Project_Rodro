package cz.rodro.dto.mapper;

import cz.rodro.dto.InstitutionDTO;
import cz.rodro.entity.InstitutionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {LocationMapper.class, OccupationMapper.class})
public interface InstitutionMapper {

    @Mapping(source = "institutionLocation", target = "institutionLocation")
    InstitutionDTO toDto(InstitutionEntity entity);

    @Mapping(source = "institutionLocation", target = "institutionLocation")
    InstitutionEntity toEntity(InstitutionDTO dto);
}

