package cz.rodro.dto.mapper;

import cz.rodro.dto.FamilyDTO;
import cz.rodro.entity.FamilyEntity;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.PersonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {PersonMapper.class, LocationMapper.class})
public interface FamilyMapper {
    FamilyDTO toFamilyDTO(FamilyEntity entity);

    @Mapping(target = "marriageLocation", ignore = true)
    @Mapping(target = "spouseMale", ignore = true)
    @Mapping(target = "spouseFemale", ignore = true)
    @Mapping(target = "witnessesMaleSide1", ignore = true)
    @Mapping(target = "witnessesMaleSide2", ignore = true)
    @Mapping(target = "witnessesFemaleSide1", ignore = true)
    @Mapping(target = "witnessesFemaleSide2", ignore = true)
    FamilyEntity toFamilyEntity(FamilyDTO dto);

    @Mapping(target = "marriageLocation", ignore = true)
    @Mapping(target = "spouseMale", ignore = true)
    @Mapping(target = "spouseFemale", ignore = true)
    @Mapping(target = "witnessesMaleSide1", ignore = true)
    @Mapping(target = "witnessesMaleSide2", ignore = true)
    @Mapping(target = "witnessesFemaleSide1", ignore = true)
    @Mapping(target = "witnessesFemaleSide2", ignore = true)
    void updateFamilyEntity(FamilyDTO dto, @MappingTarget FamilyEntity entity);
}
