package cz.rodro.dto.mapper;

import cz.rodro.dto.SubdivisionDTO;
import cz.rodro.entity.SubdivisionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = { DistrictMapper.class, LocationMapper.class })
public interface SubdivisionMapper {

    @Mapping(target = "districtName", source = "district.districtName")
    @Mapping(target = "administrativeCenterName", source = "administrativeCenter.locationName")  // Mapping admin center name
    SubdivisionDTO toSubdivisionDTO(SubdivisionEntity entity);

    SubdivisionEntity toSubdivisionEntity(SubdivisionDTO dto);

    @Mapping(target = "id", ignore = true)
    void updateSubdivisionEntity(SubdivisionDTO dto, @MappingTarget SubdivisionEntity entity);
}
