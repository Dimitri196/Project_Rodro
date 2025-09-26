package cz.rodro.dto.mapper;

import cz.rodro.dto.InstitutionDTO;
import cz.rodro.entity.InstitutionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring", uses = {LocationMapper.class, OccupationMapper.class})
public interface InstitutionMapper {

    @Mapping(source = "institutionLocation", target = "institutionLocation")
    @Mapping(source = "sealImageUrl", target = "sealImageUrl")
    InstitutionDTO toDto(InstitutionEntity entity);

    @Mapping(source = "institutionLocation", target = "institutionLocation")
    @Mapping(source = "sealImageUrl", target = "sealImageUrl")
    InstitutionEntity toEntity(InstitutionDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "sealImageUrl", target = "sealImageUrl")
    void updateInstitutionEntity(InstitutionDTO dto, @MappingTarget InstitutionEntity entity);
}
