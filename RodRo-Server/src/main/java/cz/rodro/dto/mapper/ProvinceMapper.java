package cz.rodro.dto.mapper;

import cz.rodro.dto.CountryDTO;
import cz.rodro.dto.ProvinceDTO;
import cz.rodro.entity.ProvinceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProvinceMapper {

    @Mapping(target = "country", ignore = true)  // Ignore country to prevent recursion
    @Mapping(target = "districts", ignore = true)  // Prevent circular references
    ProvinceDTO toProvinceDTO(ProvinceEntity provinceEntity);

    @Mapping(target = "country", ignore = true)
    @Mapping(target = "districts", ignore = true)
    ProvinceEntity toProvinceEntity(ProvinceDTO provinceDTO);

    @Mapping(target = "id", ignore = true)
    void updateProvinceEntity(ProvinceDTO dto, @MappingTarget ProvinceEntity entity);
}