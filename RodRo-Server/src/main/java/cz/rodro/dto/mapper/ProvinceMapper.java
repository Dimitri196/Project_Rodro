package cz.rodro.dto.mapper;

import cz.rodro.dto.ProvinceDTO;
import cz.rodro.entity.ProvinceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProvinceMapper {

    /**
     * Maps ProvinceEntity to ProvinceDTO.
     * Maps the Country entity's ID and name to the flattened DTO fields.
     * Includes the districts list for detail views.
     */
    @Mapping(source = "country.id", target = "countryId")
    @Mapping(source = "country.nameInPolish", target = "countryName") // Assuming you use nameInPolish for display
    ProvinceDTO toProvinceDTO(ProvinceEntity provinceEntity);

    /**
     * Maps ProvinceDTO to ProvinceEntity.
     * Ignores complex object fields that are not part of the DTO (country, districts).
     * The service layer must handle setting the actual CountryEntity based on countryId.
     */
    @Mapping(target = "country", ignore = true)
    @Mapping(target = "districts", ignore = true)
    ProvinceEntity toProvinceEntity(ProvinceDTO provinceDTO);

    /**
     * Updates an existing ProvinceEntity from a DTO.
     * Ignores the ID and complex relationships.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "country", ignore = true)
    @Mapping(target = "districts", ignore = true)
    void updateProvinceEntity(ProvinceDTO dto, @MappingTarget ProvinceEntity entity);
}