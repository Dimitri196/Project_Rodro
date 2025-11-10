package cz.rodro.dto.mapper;

import cz.rodro.dto.InstitutionDTO;
import cz.rodro.entity.InstitutionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {LocationMapper.class, OccupationMapper.class})
public interface InstitutionMapper {

    // 1. toDto (Entity -> DTO) ----------------------------------------------------

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "sealImageUrl", target = "sealImageUrl")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "establishmentYear", target = "establishmentYear")
    @Mapping(source = "cancellationYear", target = "cancellationYear")
    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "location.locationName", target = "locationName")
    @Mapping(source = "country.id", target = "countryId")
    @Mapping(source = "country.nameInPolish", target = "countryName")
    InstitutionDTO toDto(InstitutionEntity entity);

    // 2. toEntity (DTO -> Entity) -------------------------------------------------

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(target = "location", ignore = true)
    @Mapping(source = "sealImageUrl", target = "sealImageUrl")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "establishmentYear", target = "establishmentYear")
    @Mapping(source = "cancellationYear", target = "cancellationYear")
    @Mapping(target = "id", ignore = true)           // DTO uses @JsonProperty("_id"), so ignore simple 'id' mapping
    @Mapping(target = "country", ignore = true)
    @Mapping(target = "occupations", ignore = true)
    InstitutionEntity toEntity(InstitutionDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "country", ignore = true)
    @Mapping(target = "occupations", ignore = true)
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(target = "location", ignore = true)
    @Mapping(source = "sealImageUrl", target = "sealImageUrl")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "establishmentYear", target = "establishmentYear")
    @Mapping(source = "cancellationYear", target = "cancellationYear")

    void updateInstitutionEntity(InstitutionDTO dto, @MappingTarget InstitutionEntity entity);
}