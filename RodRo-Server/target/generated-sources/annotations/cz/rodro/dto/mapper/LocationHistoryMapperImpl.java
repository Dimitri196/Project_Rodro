package cz.rodro.dto.mapper;

import cz.rodro.dto.LocationHistoryDTO;
import cz.rodro.entity.CountryEntity;
import cz.rodro.entity.DistrictEntity;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.LocationHistoryEntity;
import cz.rodro.entity.ProvinceEntity;
import cz.rodro.entity.SubdivisionEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class LocationHistoryMapperImpl implements LocationHistoryMapper {

    @Override
    public LocationHistoryDTO toLocationHistoryDTO(LocationHistoryEntity source) {
        if ( source == null ) {
            return null;
        }

        LocationHistoryDTO locationHistoryDTO = new LocationHistoryDTO();

        locationHistoryDTO.setCountryId( sourceCountryId( source ) );
        locationHistoryDTO.setProvinceId( sourceProvinceId( source ) );
        locationHistoryDTO.setDistrictId( sourceDistrictId( source ) );
        locationHistoryDTO.setLocationName( sourceLocationLocationName( source ) );
        locationHistoryDTO.setLocationId( sourceLocationId( source ) );
        locationHistoryDTO.setSubdivisionName( sourceSubdivisionSubdivisionName( source ) );
        locationHistoryDTO.setSubdivisionId( sourceSubdivisionId( source ) );
        locationHistoryDTO.setId( source.getId() );
        locationHistoryDTO.setCountryName( source.getCountryName() );
        locationHistoryDTO.setProvinceName( source.getProvinceName() );
        locationHistoryDTO.setDistrictName( source.getDistrictName() );
        locationHistoryDTO.setStartDate( source.getStartDate() );
        locationHistoryDTO.setEndDate( source.getEndDate() );
        locationHistoryDTO.setNotes( source.getNotes() );

        return locationHistoryDTO;
    }

    @Override
    public LocationHistoryEntity toLocationHistoryEntity(LocationHistoryDTO source) {
        if ( source == null ) {
            return null;
        }

        LocationHistoryEntity locationHistoryEntity = new LocationHistoryEntity();

        locationHistoryEntity.setCountryName( source.getCountryName() );
        locationHistoryEntity.setProvinceName( source.getProvinceName() );
        locationHistoryEntity.setDistrictName( source.getDistrictName() );
        locationHistoryEntity.setSubdivisionName( source.getSubdivisionName() );
        locationHistoryEntity.setStartDate( source.getStartDate() );
        locationHistoryEntity.setEndDate( source.getEndDate() );
        locationHistoryEntity.setNotes( source.getNotes() );
        locationHistoryEntity.setId( source.getId() );

        return locationHistoryEntity;
    }

    @Override
    public void updateLocationHistoryEntity(LocationHistoryDTO dto, LocationHistoryEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setCountryName( dto.getCountryName() );
        entity.setProvinceName( dto.getProvinceName() );
        entity.setDistrictName( dto.getDistrictName() );
        entity.setSubdivisionName( dto.getSubdivisionName() );
        entity.setStartDate( dto.getStartDate() );
        entity.setEndDate( dto.getEndDate() );
        entity.setNotes( dto.getNotes() );
    }

    private Long sourceCountryId(LocationHistoryEntity locationHistoryEntity) {
        if ( locationHistoryEntity == null ) {
            return null;
        }
        CountryEntity country = locationHistoryEntity.getCountry();
        if ( country == null ) {
            return null;
        }
        Long id = country.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long sourceProvinceId(LocationHistoryEntity locationHistoryEntity) {
        if ( locationHistoryEntity == null ) {
            return null;
        }
        ProvinceEntity province = locationHistoryEntity.getProvince();
        if ( province == null ) {
            return null;
        }
        Long id = province.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long sourceDistrictId(LocationHistoryEntity locationHistoryEntity) {
        if ( locationHistoryEntity == null ) {
            return null;
        }
        DistrictEntity district = locationHistoryEntity.getDistrict();
        if ( district == null ) {
            return null;
        }
        Long id = district.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String sourceLocationLocationName(LocationHistoryEntity locationHistoryEntity) {
        if ( locationHistoryEntity == null ) {
            return null;
        }
        LocationEntity location = locationHistoryEntity.getLocation();
        if ( location == null ) {
            return null;
        }
        String locationName = location.getLocationName();
        if ( locationName == null ) {
            return null;
        }
        return locationName;
    }

    private Long sourceLocationId(LocationHistoryEntity locationHistoryEntity) {
        if ( locationHistoryEntity == null ) {
            return null;
        }
        LocationEntity location = locationHistoryEntity.getLocation();
        if ( location == null ) {
            return null;
        }
        Long id = location.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String sourceSubdivisionSubdivisionName(LocationHistoryEntity locationHistoryEntity) {
        if ( locationHistoryEntity == null ) {
            return null;
        }
        SubdivisionEntity subdivision = locationHistoryEntity.getSubdivision();
        if ( subdivision == null ) {
            return null;
        }
        String subdivisionName = subdivision.getSubdivisionName();
        if ( subdivisionName == null ) {
            return null;
        }
        return subdivisionName;
    }

    private Long sourceSubdivisionId(LocationHistoryEntity locationHistoryEntity) {
        if ( locationHistoryEntity == null ) {
            return null;
        }
        SubdivisionEntity subdivision = locationHistoryEntity.getSubdivision();
        if ( subdivision == null ) {
            return null;
        }
        Long id = subdivision.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
