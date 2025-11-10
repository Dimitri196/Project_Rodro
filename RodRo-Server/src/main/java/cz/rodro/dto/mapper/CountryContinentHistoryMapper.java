package cz.rodro.dto.mapper;

import cz.rodro.dto.CountryContinentHistoryDTO;
import cz.rodro.entity.CountryContinentHistoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CountryContinentHistoryMapper {

    // --- Entity to DTO ---
    @Mappings({
            // Extract IDs from related entities to fill the DTO fields
            @Mapping(source = "country.id", target = "countryId"),
            @Mapping(source = "continent.id", target = "continentId"),
            @Mapping(source = "continent.name", target = "continentName")
    })
    CountryContinentHistoryDTO toDTO(CountryContinentHistoryEntity entity);

    // --- DTO to Entity ---
    @Mappings({
            // Ignore the complex entity fields; the Service will populate these
            @Mapping(target = "country", ignore = true),
            @Mapping(target = "continent", ignore = true),

            // Map the simple fields directly
            @Mapping(target = "startYear", source = "startYear"),
            @Mapping(target = "endYear", source = "endYear"),

            @Mapping(target = "id", ignore = true)
    })
    CountryContinentHistoryEntity toEntity(CountryContinentHistoryDTO dto);

    // --- Update Entity from DTO ---
    @Mappings({
            @Mapping(target = "country", ignore = true), // Do not change the parent country/continent on update
            @Mapping(target = "continent", ignore = true),
            @Mapping(target = "id", ignore = true)
    })
    void updateEntityFromDTO(CountryContinentHistoryDTO dto, @MappingTarget CountryContinentHistoryEntity entity);
}
