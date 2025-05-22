package cz.rodro.dto.mapper;

import cz.rodro.dto.LocationHistoryDTO;
import cz.rodro.entity.LocationHistoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LocationHistoryMapper {

    @Mapping(target = "countryId", source = "country.id")
    @Mapping(target = "provinceId", source = "province.id")
    @Mapping(target = "districtId", source = "district.id")
    @Mapping(target = "locationName", source = "location.locationName")
    @Mapping(target = "locationId", source = "location.id")
    @Mapping(target = "subdivisionName", source = "subdivision.subdivisionName")
    @Mapping(target = "subdivisionId", source = "subdivision.id")
    LocationHistoryDTO toLocationHistoryDTO(LocationHistoryEntity source);

    // Mapping from DTO to Entity
    @Mapping(target = "location", ignore = true) // Ignore the "location" field in DTO
    @Mapping(target = "countryName", source = "countryName")
    @Mapping(target = "provinceName", source = "provinceName")
    @Mapping(target = "districtName", source = "districtName")
    @Mapping(target = "subdivisionName", source = "subdivisionName")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "notes", source = "notes")
    LocationHistoryEntity toLocationHistoryEntity(LocationHistoryDTO source);

    // Update method: Ignore 'active' while updating
    @Mapping(target = "id", ignore = true) // Ignore ID for updates
    void updateLocationHistoryEntity(LocationHistoryDTO dto, @MappingTarget LocationHistoryEntity entity);
}
