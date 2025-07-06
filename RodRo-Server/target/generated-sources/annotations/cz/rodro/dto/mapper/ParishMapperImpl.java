package cz.rodro.dto.mapper;

import cz.rodro.dto.LocationDTO;
import cz.rodro.dto.ParishDTO;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.ParishEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class ParishMapperImpl implements ParishMapper {

    @Override
    public ParishDTO toParishDTO(ParishEntity parishEntity) {
        if ( parishEntity == null ) {
            return null;
        }

        ParishDTO parishDTO = new ParishDTO();

        parishDTO.setParishLocation( locationEntityToLocationDTO( parishEntity.getParishLocation() ) );
        parishDTO.setId( parishEntity.getId() );
        parishDTO.setParishName( parishEntity.getParishName() );
        parishDTO.setParishMainChurchName( parishEntity.getParishMainChurchName() );
        parishDTO.setEstablishmentDate( parishEntity.getEstablishmentDate() );
        parishDTO.setCancellationDate( parishEntity.getCancellationDate() );

        return parishDTO;
    }

    @Override
    public ParishEntity toParishEntity(ParishDTO parishDTO) {
        if ( parishDTO == null ) {
            return null;
        }

        ParishEntity parishEntity = new ParishEntity();

        parishEntity.setParishLocation( locationDTOToLocationEntity( parishDTO.getParishLocation() ) );
        parishEntity.setId( parishDTO.getId() );
        parishEntity.setParishName( parishDTO.getParishName() );
        parishEntity.setParishMainChurchName( parishDTO.getParishMainChurchName() );
        parishEntity.setEstablishmentDate( parishDTO.getEstablishmentDate() );
        parishEntity.setCancellationDate( parishDTO.getCancellationDate() );

        return parishEntity;
    }

    @Override
    public void updateParishEntity(ParishDTO parishDTO, ParishEntity parishEntity) {
        if ( parishDTO == null ) {
            return;
        }

        parishEntity.setId( parishDTO.getId() );
        parishEntity.setParishName( parishDTO.getParishName() );
        parishEntity.setParishMainChurchName( parishDTO.getParishMainChurchName() );
        parishEntity.setEstablishmentDate( parishDTO.getEstablishmentDate() );
        parishEntity.setCancellationDate( parishDTO.getCancellationDate() );
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
