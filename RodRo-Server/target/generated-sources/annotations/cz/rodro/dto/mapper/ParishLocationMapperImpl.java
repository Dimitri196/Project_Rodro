package cz.rodro.dto.mapper;

import cz.rodro.dto.ParishLocationDTO;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.ParishEntity;
import cz.rodro.entity.ParishLocationEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class ParishLocationMapperImpl implements ParishLocationMapper {

    @Override
    public ParishLocationDTO toDto(ParishLocationEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ParishLocationDTO parishLocationDTO = new ParishLocationDTO();

        parishLocationDTO.setParishId( entityParishId( entity ) );
        parishLocationDTO.setLocationId( entityLocationId( entity ) );
        parishLocationDTO.setId( entity.getId() );
        parishLocationDTO.setMainChurchName( entity.getMainChurchName() );
        parishLocationDTO.setMainLocation( entity.isMainLocation() );

        parishLocationDTO.setParishName( entity.getParish() != null ? entity.getParish().getName() : null );
        parishLocationDTO.setLocationName( entity.getLocation() != null ? entity.getLocation().getLocationName() : null );

        return parishLocationDTO;
    }

    @Override
    public ParishLocationEntity toEntity(ParishLocationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ParishLocationEntity parishLocationEntity = new ParishLocationEntity();

        parishLocationEntity.setId( dto.getId() );
        parishLocationEntity.setParishName( dto.getParishName() );
        parishLocationEntity.setLocationName( dto.getLocationName() );
        parishLocationEntity.setMainChurchName( dto.getMainChurchName() );
        parishLocationEntity.setMainLocation( dto.isMainLocation() );

        return parishLocationEntity;
    }

    @Override
    public void updateParishLocationEntity(ParishLocationDTO dto, ParishLocationEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        entity.setParishName( dto.getParishName() );
        entity.setLocationName( dto.getLocationName() );
        entity.setMainChurchName( dto.getMainChurchName() );
        entity.setMainLocation( dto.isMainLocation() );
    }

    private Long entityParishId(ParishLocationEntity parishLocationEntity) {
        if ( parishLocationEntity == null ) {
            return null;
        }
        ParishEntity parish = parishLocationEntity.getParish();
        if ( parish == null ) {
            return null;
        }
        Long id = parish.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityLocationId(ParishLocationEntity parishLocationEntity) {
        if ( parishLocationEntity == null ) {
            return null;
        }
        LocationEntity location = parishLocationEntity.getLocation();
        if ( location == null ) {
            return null;
        }
        Long id = location.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
