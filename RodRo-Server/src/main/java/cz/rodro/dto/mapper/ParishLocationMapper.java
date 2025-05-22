package cz.rodro.dto.mapper;

import cz.rodro.dto.ParishLocationDTO;
import cz.rodro.entity.ParishLocationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ParishLocationMapper {

    @Mapping(source = "parish.id", target = "parishId")
    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "parish.parishName", target = "parishName")
    @Mapping(source = "location.locationName", target = "locationName")
    ParishLocationDTO toDto(ParishLocationEntity entity);

    @Mapping(target = "parish", ignore = true)
    @Mapping(target = "location", ignore = true)
    ParishLocationEntity toEntity(ParishLocationDTO dto);

    void updateParishLocationEntity(ParishLocationDTO dto, @MappingTarget ParishLocationEntity entity);
}