package cz.rodro.dto.mapper;

import cz.rodro.dto.ContinentDTO;
import cz.rodro.entity.ContinentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ContinentMapper {

    @Mapping(source = "id", target = "id")
    ContinentDTO toContinentDTO(ContinentEntity entity);

    @Mapping(target = "countryHistory", ignore = true)
    @Mapping(target = "id", ignore = true)
    ContinentEntity toContinentEntity(ContinentDTO dto);

    @Mapping(target = "countryHistory", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateContinentEntityFromDTO(ContinentDTO dto, @MappingTarget ContinentEntity entity);
}
