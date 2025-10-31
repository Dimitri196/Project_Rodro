package cz.rodro.dto.mapper;

import cz.rodro.dto.LocationDTO;
import cz.rodro.dto.LocationHistoryDTO;
import cz.rodro.dto.SourceDTO;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.LocationHistoryEntity;
import cz.rodro.entity.SourceEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class LocationMapperImpl implements LocationMapper {

    @Autowired
    private LocationHistoryMapper locationHistoryMapper;
    @Autowired
    private SourceMapper sourceMapper;

    @Override
    public LocationEntity toLocationEntity(LocationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        LocationEntity locationEntity = new LocationEntity();

        locationEntity.setId( dto.getId() );
        locationEntity.setLocationName( dto.getLocationName() );
        locationEntity.setEstablishmentYear( dto.getEstablishmentYear() );
        locationEntity.setGpsLatitude( dto.getGpsLatitude() );
        locationEntity.setGpsLongitude( dto.getGpsLongitude() );
        locationEntity.setSettlementType( dto.getSettlementType() );
        locationEntity.setLocationHistories( locationHistoryDTOListToLocationHistoryEntityList( dto.getLocationHistories() ) );
        locationEntity.setSources( sourceDTOListToSourceEntityList( dto.getSources() ) );
        locationEntity.setLocationImageUrl( dto.getLocationImageUrl() );

        return locationEntity;
    }

    @Override
    public LocationDTO toLocationDTO(LocationEntity entity) {
        if ( entity == null ) {
            return null;
        }

        LocationDTO locationDTO = new LocationDTO();

        locationDTO.setId( entity.getId() );
        locationDTO.setLocationName( entity.getLocationName() );
        locationDTO.setEstablishmentYear( entity.getEstablishmentYear() );
        locationDTO.setGpsLatitude( entity.getGpsLatitude() );
        locationDTO.setGpsLongitude( entity.getGpsLongitude() );
        locationDTO.setSettlementType( entity.getSettlementType() );
        locationDTO.setLocationHistories( locationHistoryEntityListToLocationHistoryDTOList( entity.getLocationHistories() ) );
        locationDTO.setSources( sourceMapper.toSourceDTOList( entity.getSources() ) );
        locationDTO.setLocationImageUrl( entity.getLocationImageUrl() );

        return locationDTO;
    }

    @Override
    public void updateLocationEntity(LocationDTO dto, LocationEntity entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getLocationName() != null ) {
            entity.setLocationName( dto.getLocationName() );
        }
        if ( dto.getEstablishmentYear() != null ) {
            entity.setEstablishmentYear( dto.getEstablishmentYear() );
        }
        if ( dto.getGpsLatitude() != null ) {
            entity.setGpsLatitude( dto.getGpsLatitude() );
        }
        if ( dto.getGpsLongitude() != null ) {
            entity.setGpsLongitude( dto.getGpsLongitude() );
        }
        if ( dto.getSettlementType() != null ) {
            entity.setSettlementType( dto.getSettlementType() );
        }
        if ( dto.getLocationImageUrl() != null ) {
            entity.setLocationImageUrl( dto.getLocationImageUrl() );
        }
    }

    protected List<LocationHistoryEntity> locationHistoryDTOListToLocationHistoryEntityList(List<LocationHistoryDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<LocationHistoryEntity> list1 = new ArrayList<LocationHistoryEntity>( list.size() );
        for ( LocationHistoryDTO locationHistoryDTO : list ) {
            list1.add( locationHistoryMapper.toLocationHistoryEntity( locationHistoryDTO ) );
        }

        return list1;
    }

    protected List<SourceEntity> sourceDTOListToSourceEntityList(List<SourceDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<SourceEntity> list1 = new ArrayList<SourceEntity>( list.size() );
        for ( SourceDTO sourceDTO : list ) {
            list1.add( sourceMapper.toSourceEntity( sourceDTO ) );
        }

        return list1;
    }

    protected List<LocationHistoryDTO> locationHistoryEntityListToLocationHistoryDTOList(List<LocationHistoryEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<LocationHistoryDTO> list1 = new ArrayList<LocationHistoryDTO>( list.size() );
        for ( LocationHistoryEntity locationHistoryEntity : list ) {
            list1.add( locationHistoryMapper.toLocationHistoryDTO( locationHistoryEntity ) );
        }

        return list1;
    }
}
