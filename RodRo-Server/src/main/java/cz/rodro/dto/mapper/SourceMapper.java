package cz.rodro.dto.mapper;

import cz.rodro.dto.SourceDTO;
import cz.rodro.entity.SourceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SourceMapper {

    @Mapping(target = "sourceLocation", source = "sourceLocation")
    SourceDTO toSourceDTO(SourceEntity entity);

    @Mapping(target = "sourceLocation", source = "sourceLocation")
    SourceEntity toSourceEntity(SourceDTO dto);

    @Mapping(target = "sourceLocation", source = "sourceLocation", ignore = true)
    void updateSourceEntity(SourceDTO dto, @MappingTarget SourceEntity entity);

    List<SourceDTO> toSourceDTOs(List<SourceEntity> entities);
    List<SourceEntity> toSourceEntities(List<SourceDTO> dtos);
}