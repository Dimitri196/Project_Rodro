package cz.rodro.dto.mapper;

import cz.rodro.dto.MilitaryStructureDTO;
import cz.rodro.entity.MilitaryStructureEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = MilitaryOrganizationMapper.class)
public interface MilitaryStructureMapper {

    @Mapping(target = "organization.country.provinces", ignore = true)
    MilitaryStructureDTO toMilitaryStructureDTO(MilitaryStructureEntity entity);

    MilitaryStructureEntity toMilitaryStructureEntity(MilitaryStructureDTO dto);

    void updateMilitaryStructureEntity(MilitaryStructureDTO dto, @MappingTarget MilitaryStructureEntity entity);
}
