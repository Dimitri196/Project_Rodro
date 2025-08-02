package cz.rodro.dto.mapper;

import cz.rodro.dto.SourceDTO;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.SourceEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class SourceMapperImpl implements SourceMapper {

    @Override
    public SourceEntity toSourceEntity(SourceDTO source) {
        if ( source == null ) {
            return null;
        }

        SourceEntity sourceEntity = new SourceEntity();

        sourceEntity.setSourceLocation( map( source.getSourceLocationId() ) );
        sourceEntity.setId( source.getId() );
        sourceEntity.setSourceTitle( source.getSourceTitle() );
        sourceEntity.setSourceDescription( source.getSourceDescription() );
        sourceEntity.setSourceReference( source.getSourceReference() );
        sourceEntity.setSourceType( source.getSourceType() );
        sourceEntity.setSourceUrl( source.getSourceUrl() );

        return sourceEntity;
    }

    @Override
    public SourceDTO toSourceDTO(SourceEntity source) {
        if ( source == null ) {
            return null;
        }

        SourceDTO sourceDTO = new SourceDTO();

        sourceDTO.setSourceLocationId( sourceSourceLocationId( source ) );
        sourceDTO.setSourceLocationName( sourceSourceLocationLocationName( source ) );
        sourceDTO.setId( source.getId() );
        sourceDTO.setSourceTitle( source.getSourceTitle() );
        sourceDTO.setSourceReference( source.getSourceReference() );
        sourceDTO.setSourceDescription( source.getSourceDescription() );
        sourceDTO.setSourceUrl( source.getSourceUrl() );
        sourceDTO.setSourceType( source.getSourceType() );

        return sourceDTO;
    }

    @Override
    public void updateSourceEntity(SourceDTO dto, SourceEntity entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getSourceLocationId() != null ) {
            entity.setSourceLocation( map( dto.getSourceLocationId() ) );
        }
        if ( dto.getSourceTitle() != null ) {
            entity.setSourceTitle( dto.getSourceTitle() );
        }
        if ( dto.getSourceDescription() != null ) {
            entity.setSourceDescription( dto.getSourceDescription() );
        }
        if ( dto.getSourceReference() != null ) {
            entity.setSourceReference( dto.getSourceReference() );
        }
        if ( dto.getSourceType() != null ) {
            entity.setSourceType( dto.getSourceType() );
        }
        if ( dto.getSourceUrl() != null ) {
            entity.setSourceUrl( dto.getSourceUrl() );
        }
    }

    private Long sourceSourceLocationId(SourceEntity sourceEntity) {
        if ( sourceEntity == null ) {
            return null;
        }
        LocationEntity sourceLocation = sourceEntity.getSourceLocation();
        if ( sourceLocation == null ) {
            return null;
        }
        Long id = sourceLocation.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String sourceSourceLocationLocationName(SourceEntity sourceEntity) {
        if ( sourceEntity == null ) {
            return null;
        }
        LocationEntity sourceLocation = sourceEntity.getSourceLocation();
        if ( sourceLocation == null ) {
            return null;
        }
        String locationName = sourceLocation.getLocationName();
        if ( locationName == null ) {
            return null;
        }
        return locationName;
    }
}
