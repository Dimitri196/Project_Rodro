package cz.rodro.dto.mapper;

import cz.rodro.dto.FamilyDTO;
import cz.rodro.entity.FamilyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FamilyMapper {

    FamilyEntity toFamilyEntity(FamilyDTO source);

    FamilyDTO toFamilyDTO(FamilyEntity source);

    @Mapping(target = "marriageLocation", ignore = true)
    @Mapping(target = "spouseMale", ignore = true)
    @Mapping(target = "spouseFemale", ignore = true)
    void updateFamilyEntity(FamilyDTO source, @MappingTarget FamilyEntity target);

}
