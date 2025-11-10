package cz.rodro.dto.mapper;

import cz.rodro.dto.MilitaryOrganizationDTO;
import cz.rodro.entity.MilitaryOrganizationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface MilitaryOrganizationMapper {


    @Mapping(target = "id", source = "id")
    @Mapping(source = "country.id", target = "countryId")
    @Mapping(source = "country.nameInEnglish", target = "countryName")
    @Mapping(target = "armyBranch", source = "armyBranch")
    @Mapping(target = "structures", source = "structures")
    MilitaryOrganizationDTO toMilitaryOrganizationDTO(MilitaryOrganizationEntity entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "armyBranch", ignore = true)     // Service sets this
    @Mapping(target = "militaryRanks", ignore = true)  // Managed by Entity
    @Mapping(target = "structures", ignore = true)     // Managed by Entity
    MilitaryOrganizationEntity toMilitaryOrganizationEntity(MilitaryOrganizationDTO dto);

    void updateMilitaryOrganizationEntity(MilitaryOrganizationDTO dto, @MappingTarget MilitaryOrganizationEntity entity);
}
