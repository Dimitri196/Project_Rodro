package cz.rodro.dto.mapper;

import cz.rodro.dto.SourceDTO;
import cz.rodro.entity.SourceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SourceMapper {

    SourceDTO toSourceDTO(SourceEntity entity);
    SourceEntity toSourceEntity(SourceDTO dto);

    void updateSourceEntity(SourceDTO dto, @MappingTarget SourceEntity entity);

    List<SourceDTO> toSourceDTOs(List<SourceEntity> entities);
    List<SourceEntity> toSourceEntities(List<SourceDTO> dtos);
}