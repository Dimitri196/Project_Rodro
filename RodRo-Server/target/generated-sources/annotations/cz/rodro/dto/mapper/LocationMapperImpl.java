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
    public LocationEntity toLocationEntity(LocationDTO source) {
        if ( source == null ) {
            return null;
        }

        LocationEntity locationEntity = new LocationEntity();

        locationEntity.setId( source.getId() );
        locationEntity.setLocationName( source.getLocationName() );
        locationEntity.setEstablishmentYear( source.getEstablishmentYear() );
        locationEntity.setGpsLatitude( source.getGpsLatitude() );
        locationEntity.setGpsLongitude( source.getGpsLongitude() );
        locationEntity.setSettlementType( source.getSettlementType() );
        locationEntity.setLocationHistories( locationHistoryDTOListToLocationHistoryEntityList( source.getLocationHistories() ) );
        locationEntity.setSources( sourceDTOListToSourceEntityList( source.getSources() ) );

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
        locationDTO.setEstablishmentYear( source.getEstablishmentYear() );
        locationDTO.setGpsLatitude( source.getGpsLatitude() );
        locationDTO.setGpsLongitude( source.getGpsLongitude() );
        locationDTO.setSettlementType( source.getSettlementType() );
        locationDTO.setLocationHistories( locationHistoryEntityListToLocationHistoryDTOList( source.getLocationHistories() ) );
        locationDTO.setSources( sourceEntityListToSourceDTOList( source.getSources() ) );

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

    protected List<SourceDTO> sourceEntityListToSourceDTOList(List<SourceEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<SourceDTO> list1 = new ArrayList<SourceDTO>( list.size() );
        for ( SourceEntity sourceEntity : list ) {
            list1.add( sourceMapper.toSourceDTO( sourceEntity ) );
        }

        return list1;
    }
}
