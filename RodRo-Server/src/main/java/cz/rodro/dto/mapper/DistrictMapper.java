package cz.rodro.dto.mapper;

import cz.rodro.dto.DistrictDTO;
import cz.rodro.entity.DistrictEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DistrictMapper {

    @Mapping(target = "province", ignore = true)  // Prevent circular reference
    DistrictDTO toDistrictDTO(DistrictEntity districtEntity);

    @Mapping(target = "province", ignore = true)
    DistrictEntity toDistrictEntity(DistrictDTO districtDTO);

    void updateDistrictEntity(DistrictDTO districtDTO, @MappingTarget DistrictEntity districtEntity);
}
