package cz.rodro.dto.mapper;

import cz.rodro.dto.CountryDTO;
import cz.rodro.entity.CountryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    @Mapping(target = "provinces", ignore = true)
    CountryDTO toCountryDTO(CountryEntity countryEntity);

    CountryEntity toCountryEntity(CountryDTO countryDTO);
}