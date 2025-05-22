package cz.rodro.dto.mapper;

import cz.rodro.dto.ParishDTO;
import cz.rodro.entity.ParishEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ParishMapper {

    // Mapping from ParishEntity to ParishDTO
    @Mapping(target = "parishLocation", source = "parishLocation")
    ParishDTO toParishDTO(ParishEntity parishEntity);

    // Mapping from ParishDTO to ParishEntity
    @Mapping(target = "parishLocation", source = "parishLocation")
    ParishEntity toParishEntity(ParishDTO parishDTO);

    // Mapping to update ParishEntity
    @Mapping(target = "parishLocation", source = "parishLocation", ignore = true)
    void updateParishEntity(ParishDTO parishDTO, @MappingTarget ParishEntity parishEntity);
}