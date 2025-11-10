package cz.rodro.dto.mapper;

import cz.rodro.dto.SubdivisionDTO;
import cz.rodro.entity.SubdivisionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = { DistrictMapper.class, LocationMapper.class })
public interface SubdivisionMapper {

    @Mapping(target = "districtName", source = "district.name")
    @Mapping(target = "administrativeCenterName", source = "administrativeCenter.locationName")
    @Mapping(target = "district.provinceId", source = "district.province.id")   // add provinceId
    @Mapping(target = "district.countryId", source = "district.province.country.id") // add countryId
    SubdivisionDTO toSubdivisionDTO(SubdivisionEntity entity);

    SubdivisionEntity toSubdivisionEntity(SubdivisionDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateSubdivisionEntity(SubdivisionDTO dto, @MappingTarget SubdivisionEntity entity);
}