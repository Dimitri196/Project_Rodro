package cz.rodro.dto.mapper;

import cz.rodro.dto.CemeteryDTO;
import cz.rodro.dto.LocationDTO;
import cz.rodro.entity.CemeteryEntity;
import cz.rodro.entity.LocationEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class CemeteryMapperImpl implements CemeteryMapper {

    @Override
    public CemeteryDTO toCemeteryDTO(CemeteryEntity entity) {
        if ( entity == null ) {
            return null;
        }

        CemeteryDTO cemeteryDTO = new CemeteryDTO();

        cemeteryDTO.setCemeteryLocation( locationEntityToLocationDTO( entity.getCemeteryLocation() ) );
        cemeteryDTO.setId( entity.getId() );
        cemeteryDTO.setCemeteryName( entity.getCemeteryName() );
        cemeteryDTO.setDescription( entity.getDescription() );
        cemeteryDTO.setWebLink( entity.getWebLink() );

        return cemeteryDTO;
    }

    @Override
    public CemeteryEntity toCemeteryEntity(CemeteryDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CemeteryEntity cemeteryEntity = new CemeteryEntity();

        cemeteryEntity.setCemeteryLocation( locationDTOToLocationEntity( dto.getCemeteryLocation() ) );
        cemeteryEntity.setId( dto.getId() );
        cemeteryEntity.setCemeteryName( dto.getCemeteryName() );
        cemeteryEntity.setDescription( dto.getDescription() );
        cemeteryEntity.setWebLink( dto.getWebLink() );

        return cemeteryEntity;
    }

    @Override
    public void updateCemeteryEntity(CemeteryDTO dto, CemeteryEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setCemeteryName( dto.getCemeteryName() );
        entity.setDescription( dto.getDescription() );
        entity.setWebLink( dto.getWebLink() );
    }

    protected LocationDTO locationEntityToLocationDTO(LocationEntity locationEntity) {
        if ( locationEntity == null ) {
            return null;
        }

        LocationDTO locationDTO = new LocationDTO();

        locationDTO.setId( locationEntity.getId() );
        locationDTO.setLocationName( locationEntity.getLocationName() );
        locationDTO.setEstablishmentDate( locationEntity.getEstablishmentDate() );
        locationDTO.setSettlementType( locationEntity.getSettlementType() );
        locationDTO.setGpsLatitude( locationEntity.getGpsLatitude() );
        locationDTO.setGpsLongitude( locationEntity.getGpsLongitude() );

        return locationDTO;
    }

    protected LocationEntity locationDTOToLocationEntity(LocationDTO locationDTO) {
        if ( locationDTO == null ) {
            return null;
        }

        LocationEntity locationEntity = new LocationEntity();

        locationEntity.setId( locationDTO.getId() );
        locationEntity.setLocationName( locationDTO.getLocationName() );
        locationEntity.setEstablishmentDate( locationDTO.getEstablishmentDate() );
        locationEntity.setGpsLatitude( locationDTO.getGpsLatitude() );
        locationEntity.setGpsLongitude( locationDTO.getGpsLongitude() );
        locationEntity.setSettlementType( locationDTO.getSettlementType() );

        return locationEntity;
    }
}
