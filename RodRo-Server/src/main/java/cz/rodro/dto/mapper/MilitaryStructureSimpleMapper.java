package cz.rodro.dto.mapper;

import cz.rodro.dto.MilitaryStructureSimpleDTO;
import cz.rodro.entity.MilitaryStructureEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface MilitaryStructureSimpleMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "unitName", target = "unitName")
    MilitaryStructureSimpleDTO toMilitaryStructureSimpleDTO(MilitaryStructureEntity entity);
}