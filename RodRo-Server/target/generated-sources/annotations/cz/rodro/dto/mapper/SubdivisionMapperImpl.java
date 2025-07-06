package cz.rodro.dto.mapper;

import cz.rodro.dto.SubdivisionDTO;
import cz.rodro.entity.DistrictEntity;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.SubdivisionEntity;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class SubdivisionMapperImpl implements SubdivisionMapper {

    @Autowired
    private DistrictMapper districtMapper;
    @Autowired
    private LocationMapper locationMapper;

    @Override
    public SubdivisionDTO toSubdivisionDTO(SubdivisionEntity entity) {
        if ( entity == null ) {
            return null;
        }

        SubdivisionDTO subdivisionDTO = new SubdivisionDTO();

        subdivisionDTO.setDistrictName( entityDistrictDistrictName( entity ) );
        subdivisionDTO.setAdministrativeCenterName( entityAdministrativeCenterLocationName( entity ) );
        subdivisionDTO.setId( entity.getId() );
        subdivisionDTO.setSubdivisionName( entity.getSubdivisionName() );
        subdivisionDTO.setDistrict( districtMapper.toDistrictDTO( entity.getDistrict() ) );
        subdivisionDTO.setAdministrativeCenter( locationMapper.toLocationDTO( entity.getAdministrativeCenter() ) );
        subdivisionDTO.setSubdivisionEstablishmentYear( entity.getSubdivisionEstablishmentYear() );
        subdivisionDTO.setSubdivisionCancellationYear( entity.getSubdivisionCancellationYear() );

        return subdivisionDTO;
    }

    @Override
    public SubdivisionEntity toSubdivisionEntity(SubdivisionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        SubdivisionEntity subdivisionEntity = new SubdivisionEntity();

        subdivisionEntity.setId( dto.getId() );
        subdivisionEntity.setSubdivisionName( dto.getSubdivisionName() );
        subdivisionEntity.setDistrict( districtMapper.toDistrictEntity( dto.getDistrict() ) );
        subdivisionEntity.setAdministrativeCenter( locationMapper.toLocationEntity( dto.getAdministrativeCenter() ) );
        subdivisionEntity.setSubdivisionEstablishmentYear( dto.getSubdivisionEstablishmentYear() );
        subdivisionEntity.setSubdivisionCancellationYear( dto.getSubdivisionCancellationYear() );

        return subdivisionEntity;
    }

    @Override
    public void updateSubdivisionEntity(SubdivisionDTO dto, SubdivisionEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setSubdivisionName( dto.getSubdivisionName() );
        if ( dto.getDistrict() != null ) {
            if ( entity.getDistrict() == null ) {
                entity.setDistrict( new DistrictEntity() );
            }
            districtMapper.updateDistrictEntity( dto.getDistrict(), entity.getDistrict() );
        }
        else {
            entity.setDistrict( null );
        }
        if ( dto.getAdministrativeCenter() != null ) {
            if ( entity.getAdministrativeCenter() == null ) {
                entity.setAdministrativeCenter( new LocationEntity() );
            }
            locationMapper.updateLocationEntity( dto.getAdministrativeCenter(), entity.getAdministrativeCenter() );
        }
        else {
            entity.setAdministrativeCenter( null );
        }
        entity.setSubdivisionEstablishmentYear( dto.getSubdivisionEstablishmentYear() );
        entity.setSubdivisionCancellationYear( dto.getSubdivisionCancellationYear() );
    }

    private String entityDistrictDistrictName(SubdivisionEntity subdivisionEntity) {
        if ( subdivisionEntity == null ) {
            return null;
        }
        DistrictEntity district = subdivisionEntity.getDistrict();
        if ( district == null ) {
            return null;
        }
        String districtName = district.getDistrictName();
        if ( districtName == null ) {
            return null;
        }
        return districtName;
    }

    private String entityAdministrativeCenterLocationName(SubdivisionEntity subdivisionEntity) {
        if ( subdivisionEntity == null ) {
            return null;
        }
        LocationEntity administrativeCenter = subdivisionEntity.getAdministrativeCenter();
        if ( administrativeCenter == null ) {
            return null;
        }
        String locationName = administrativeCenter.getLocationName();
        if ( locationName == null ) {
            return null;
        }
        return locationName;
    }
}
