package cz.rodro.dto.mapper;

import cz.rodro.dto.LocationDTO;
import cz.rodro.entity.LocationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = { LocationHistoryMapper.class, DistrictMapper.class })
public interface LocationMapper {

    LocationEntity toLocationEntity(LocationDTO source);

    LocationDTO toLocationDTO(LocationEntity source);

    @Mapping(target = "id", ignore = true)
    void updateLocationEntity(LocationDTO dto, @MappingTarget LocationEntity entity);
}