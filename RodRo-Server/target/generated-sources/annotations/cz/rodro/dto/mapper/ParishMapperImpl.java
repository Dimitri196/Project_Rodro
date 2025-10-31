package cz.rodro.dto.mapper;

import cz.rodro.dto.ParishDTO;
import cz.rodro.dto.ParishLocationDTO;
import cz.rodro.entity.ParishEntity;
import cz.rodro.entity.ParishLocationEntity;
import java.util.ArrayList;
import java.util.List;
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

        parishDTO.setId( parishEntity.getId() );
        parishDTO.setName( parishEntity.getName() );
        parishDTO.setMainChurchName( parishEntity.getMainChurchName() );
        parishDTO.setEstablishmentYear( parishEntity.getEstablishmentYear() );
        parishDTO.setCancellationYear( parishEntity.getCancellationYear() );
        parishDTO.setLocations( parishLocationEntityListToParishLocationDTOList( parishEntity.getLocations() ) );
        parishDTO.setChurchImageUrl( parishEntity.getChurchImageUrl() );
        parishDTO.setDescription( parishEntity.getDescription() );
        parishDTO.setConfession( parishEntity.getConfession() );

        return parishDTO;
    }

    @Override
    public ParishEntity toParishEntity(ParishDTO parishDTO) {
        if ( parishDTO == null ) {
            return null;
        }

        ParishEntity parishEntity = new ParishEntity();

        parishEntity.setId( parishDTO.getId() );
        parishEntity.setName( parishDTO.getName() );
        parishEntity.setMainChurchName( parishDTO.getMainChurchName() );
        parishEntity.setEstablishmentYear( parishDTO.getEstablishmentYear() );
        parishEntity.setCancellationYear( parishDTO.getCancellationYear() );
        parishEntity.setChurchImageUrl( parishDTO.getChurchImageUrl() );
        parishEntity.setDescription( parishDTO.getDescription() );
        parishEntity.setConfession( parishDTO.getConfession() );
        parishEntity.setLocations( parishLocationDTOListToParishLocationEntityList( parishDTO.getLocations() ) );

        return parishEntity;
    }

    @Override
    public void updateParishEntity(ParishDTO parishDTO, ParishEntity parishEntity) {
        if ( parishDTO == null ) {
            return;
        }

        parishEntity.setId( parishDTO.getId() );
        parishEntity.setName( parishDTO.getName() );
        parishEntity.setMainChurchName( parishDTO.getMainChurchName() );
        parishEntity.setEstablishmentYear( parishDTO.getEstablishmentYear() );
        parishEntity.setCancellationYear( parishDTO.getCancellationYear() );
        parishEntity.setChurchImageUrl( parishDTO.getChurchImageUrl() );
        parishEntity.setDescription( parishDTO.getDescription() );
        parishEntity.setConfession( parishDTO.getConfession() );
        if ( parishEntity.getLocations() != null ) {
            List<ParishLocationEntity> list = parishLocationDTOListToParishLocationEntityList( parishDTO.getLocations() );
            if ( list != null ) {
                parishEntity.getLocations().clear();
                parishEntity.getLocations().addAll( list );
            }
            else {
                parishEntity.setLocations( null );
            }
        }
        else {
            List<ParishLocationEntity> list = parishLocationDTOListToParishLocationEntityList( parishDTO.getLocations() );
            if ( list != null ) {
                parishEntity.setLocations( list );
            }
        }
    }

    protected ParishLocationDTO parishLocationEntityToParishLocationDTO(ParishLocationEntity parishLocationEntity) {
        if ( parishLocationEntity == null ) {
            return null;
        }

        ParishLocationDTO parishLocationDTO = new ParishLocationDTO();

        parishLocationDTO.setId( parishLocationEntity.getId() );
        parishLocationDTO.setParishName( parishLocationEntity.getParishName() );
        parishLocationDTO.setLocationName( parishLocationEntity.getLocationName() );
        parishLocationDTO.setMainChurchName( parishLocationEntity.getMainChurchName() );
        parishLocationDTO.setMainLocation( parishLocationEntity.isMainLocation() );

        return parishLocationDTO;
    }

    protected List<ParishLocationDTO> parishLocationEntityListToParishLocationDTOList(List<ParishLocationEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<ParishLocationDTO> list1 = new ArrayList<ParishLocationDTO>( list.size() );
        for ( ParishLocationEntity parishLocationEntity : list ) {
            list1.add( parishLocationEntityToParishLocationDTO( parishLocationEntity ) );
        }

        return list1;
    }

    protected ParishLocationEntity parishLocationDTOToParishLocationEntity(ParishLocationDTO parishLocationDTO) {
        if ( parishLocationDTO == null ) {
            return null;
        }

        ParishLocationEntity parishLocationEntity = new ParishLocationEntity();

        parishLocationEntity.setId( parishLocationDTO.getId() );
        parishLocationEntity.setParishName( parishLocationDTO.getParishName() );
        parishLocationEntity.setLocationName( parishLocationDTO.getLocationName() );
        parishLocationEntity.setMainChurchName( parishLocationDTO.getMainChurchName() );
        parishLocationEntity.setMainLocation( parishLocationDTO.isMainLocation() );

        return parishLocationEntity;
    }

    protected List<ParishLocationEntity> parishLocationDTOListToParishLocationEntityList(List<ParishLocationDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<ParishLocationEntity> list1 = new ArrayList<ParishLocationEntity>( list.size() );
        for ( ParishLocationDTO parishLocationDTO : list ) {
            list1.add( parishLocationDTOToParishLocationEntity( parishLocationDTO ) );
        }

        return list1;
    }
}
