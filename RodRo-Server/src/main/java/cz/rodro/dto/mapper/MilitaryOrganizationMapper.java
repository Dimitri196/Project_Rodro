package cz.rodro.dto.mapper;

import cz.rodro.dto.MilitaryOrganizationDTO;
import cz.rodro.entity.MilitaryOrganizationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = CountryMapper.class)
public interface MilitaryOrganizationMapper {

    MilitaryOrganizationDTO toMilitaryOrganizationDTO(MilitaryOrganizationEntity entity);

    MilitaryOrganizationEntity toMilitaryOrganizationEntity(MilitaryOrganizationDTO dto);
    void updateMilitaryOrganizationEntity(MilitaryOrganizationDTO dto, @MappingTarget MilitaryOrganizationEntity entity);
}