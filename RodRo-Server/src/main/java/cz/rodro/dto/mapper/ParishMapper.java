package cz.rodro.dto.mapper;

import cz.rodro.dto.ParishDTO;
import cz.rodro.entity.ParishEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ParishMapper {

    // This mapping from Entity to DTO is now correct and avoids the circular loop
    // It ignores the back-reference to the parent LocationEntity
    @Mapping(target = "parishLocation", ignore = true)
    ParishDTO toParishDTO(ParishEntity parishEntity);

    // This method is no longer valid because the DTO contains a LocationDTO
    // that doesn't map to a simple field in the Entity.
    // The logic to create/update ParishLocationEntities must be handled in your service.
    // Therefore, this method needs to be re-evaluated or removed.
    // For now, let's remove the incorrect mapping that's causing the error.
    ParishEntity toParishEntity(ParishDTO parishDTO);

    // This mapping for updates is no longer needed since the field was removed
    void updateParishEntity(ParishDTO parishDTO, @MappingTarget ParishEntity parishEntity);
}