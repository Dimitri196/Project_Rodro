package cz.rodro.dto.mapper;

import cz.rodro.dto.CemeteryDTO;
import cz.rodro.entity.CemeteryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CemeteryMapper {

    // Mapping from CemeteryEntity to CemeteryDTO
    @Mapping(target = "cemeteryLocation", source = "cemeteryLocation")
    CemeteryDTO toCemeteryDTO(CemeteryEntity entity);

    // Mapping from CemeteryDTO to CemeteryEntity
    @Mapping(target = "cemeteryLocation", source = "cemeteryLocation")
    CemeteryEntity toCemeteryEntity(CemeteryDTO dto);

    // Update method (ignore cemetery ID and location)
    @Mapping(target = "id", ignore = true)  // Ignore ID in updates
    @Mapping(target = "cemeteryLocation", ignore = true)
    void updateCemeteryEntity(CemeteryDTO dto, @MappingTarget CemeteryEntity entity);
}