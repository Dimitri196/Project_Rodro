package cz.rodro.dto.mapper;

import cz.rodro.dto.MilitaryOrganizationDTO;
import cz.rodro.entity.MilitaryOrganizationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = { MilitaryStructureMapper.class, CountryMapper.class })
public interface MilitaryOrganizationMapper {

    @Mapping(target = "country.provinces", ignore = true) // break country loop
    MilitaryOrganizationDTO toMilitaryOrganizationDTO(MilitaryOrganizationEntity entity);

    MilitaryOrganizationEntity toMilitaryOrganizationEntity(MilitaryOrganizationDTO dto);

    void updateMilitaryOrganizationEntity(MilitaryOrganizationDTO dto, @MappingTarget MilitaryOrganizationEntity entity);
}