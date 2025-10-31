package cz.rodro.dto.mapper;

import cz.rodro.dto.SourceAttributionDTO;
import cz.rodro.entity.SourceAttributionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SourceAttributionMapper {

    @Mapping(target = "sourceId", source = "source.id")
    // ADD THESE MISSING MAPPINGS:
    @Mapping(target = "personId", source = "person.id")
    @Mapping(target = "occupationId", source = "occupation.id")
    @Mapping(target = "familyId", source = "family.id")
    @Mapping(target = "militaryServiceId", source = "militaryService.id")
    SourceAttributionDTO toDTO(SourceAttributionEntity entity);

    @Mapping(target = "source", ignore = true)
    @Mapping(target = "person", ignore = true)
    @Mapping(target = "occupation", ignore = true)
    @Mapping(target = "family", ignore = true)
    @Mapping(target = "militaryService", ignore = true)
    SourceAttributionEntity toEntity(SourceAttributionDTO dto);
}