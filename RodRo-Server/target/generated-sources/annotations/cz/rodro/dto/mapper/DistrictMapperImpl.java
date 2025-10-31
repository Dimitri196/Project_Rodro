package cz.rodro.dto.mapper;

import cz.rodro.dto.DistrictDTO;
import cz.rodro.entity.CountryEntity;
import cz.rodro.entity.DistrictEntity;
import cz.rodro.entity.ProvinceEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class DistrictMapperImpl implements DistrictMapper {

    @Override
    public DistrictDTO toDistrictDTO(DistrictEntity districtEntity) {
        if ( districtEntity == null ) {
            return null;
        }

        DistrictDTO districtDTO = new DistrictDTO();

        districtDTO.setProvinceId( districtEntityProvinceId( districtEntity ) );
        districtDTO.setCountryId( districtEntityProvinceCountryId( districtEntity ) );
        districtDTO.setId( districtEntity.getId() );
        districtDTO.setDistrictName( districtEntity.getDistrictName() );

        return districtDTO;
    }

    @Override
    public DistrictEntity toDistrictEntity(DistrictDTO districtDTO) {
        if ( districtDTO == null ) {
            return null;
        }

        DistrictEntity districtEntity = new DistrictEntity();

        districtEntity.setId( districtDTO.getId() );
        districtEntity.setDistrictName( districtDTO.getDistrictName() );

        return districtEntity;
    }

    @Override
    public void updateDistrictEntity(DistrictDTO districtDTO, DistrictEntity districtEntity) {
        if ( districtDTO == null ) {
            return;
        }

        districtEntity.setId( districtDTO.getId() );
        districtEntity.setDistrictName( districtDTO.getDistrictName() );
    }

    private Long districtEntityProvinceId(DistrictEntity districtEntity) {
        if ( districtEntity == null ) {
            return null;
        }
        ProvinceEntity province = districtEntity.getProvince();
        if ( province == null ) {
            return null;
        }
        Long id = province.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long districtEntityProvinceCountryId(DistrictEntity districtEntity) {
        if ( districtEntity == null ) {
            return null;
        }
        ProvinceEntity province = districtEntity.getProvince();
        if ( province == null ) {
            return null;
        }
        CountryEntity country = province.getCountry();
        if ( country == null ) {
            return null;
        }
        Long id = country.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
