package cz.rodro.dto.mapper;

import cz.rodro.dto.LocationDTO;
import cz.rodro.entity.LocationEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class LocationMapperImpl implements LocationMapper {

    @Override
    public LocationEntity toLocationEntity(LocationDTO source) {
        if ( source == null ) {
            return null;
        }

        LocationEntity locationEntity = new LocationEntity();

        locationEntity.setId( source.getId() );
        locationEntity.setLocationName( source.getLocationName() );
        locationEntity.setEstablishmentDate( source.getEstablishmentDate() );
        locationEntity.setGpsLatitude( source.getGpsLatitude() );
        locationEntity.setGpsLongitude( source.getGpsLongitude() );
        locationEntity.setSettlementType( source.getSettlementType() );

        return locationEntity;
    }

    @Override
    public LocationDTO toLocationDTO(LocationEntity source) {
        if ( source == null ) {
            return null;
        }

        LocationDTO locationDTO = new LocationDTO();

        locationDTO.setId( source.getId() );
        locationDTO.setLocationName( source.getLocationName() );
        locationDTO.setEstablishmentDate( source.getEstablishmentDate() );
        locationDTO.setSettlementType( source.getSettlementType() );
        locationDTO.setGpsLatitude( source.getGpsLatitude() );
        locationDTO.setGpsLongitude( source.getGpsLongitude() );

        return locationDTO;
    }

    @Override
    public void updateLocationEntity(LocationDTO dto, LocationEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setLocationName( dto.getLocationName() );
        entity.setEstablishmentDate( dto.getEstablishmentDate() );
        entity.setGpsLatitude( dto.getGpsLatitude() );
        entity.setGpsLongitude( dto.getGpsLongitude() );
        entity.setSettlementType( dto.getSettlementType() );
    }
}
