package cz.rodro.dto.mapper;

import cz.rodro.dto.SourceDTO;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.SourceEntity;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

        SourceEntity.SourceEntityBuilder sourceEntity = SourceEntity.builder();

        sourceEntity.location( map( source.getLocationId() ) );
        sourceEntity.id( source.getId() );
        sourceEntity.title( source.getTitle() );
        sourceEntity.description( source.getDescription() );
        sourceEntity.reference( source.getReference() );
        sourceEntity.type( source.getType() );
        sourceEntity.url( source.getUrl() );
        sourceEntity.creationYear( source.getCreationYear() );
        sourceEntity.startYear( source.getStartYear() );
        sourceEntity.endYear( source.getEndYear() );
        Map<String, Object> map = source.getMetadata();
        if ( map != null ) {
            sourceEntity.metadata( new LinkedHashMap<String, Object>( map ) );
        }
        sourceEntity.citationString( source.getCitationString() );

        return sourceEntity.build();
    }

    @Override
    public SourceDTO toSourceDTO(SourceEntity source) {
        if ( source == null ) {
            return null;
        }

        SourceDTO sourceDTO = new SourceDTO();

        sourceDTO.setLocationId( sourceLocationId( source ) );
        sourceDTO.setLocationName( sourceLocationLocationName( source ) );
        sourceDTO.setId( source.getId() );
        sourceDTO.setTitle( source.getTitle() );
        sourceDTO.setReference( source.getReference() );
        sourceDTO.setDescription( source.getDescription() );
        sourceDTO.setUrl( source.getUrl() );
        sourceDTO.setType( source.getType() );
        sourceDTO.setCreationYear( source.getCreationYear() );
        sourceDTO.setStartYear( source.getStartYear() );
        sourceDTO.setEndYear( source.getEndYear() );
        Map<String, Object> map = source.getMetadata();
        if ( map != null ) {
            sourceDTO.setMetadata( new LinkedHashMap<String, Object>( map ) );
        }
        sourceDTO.setCitationString( source.getCitationString() );

        return sourceDTO;
    }

    @Override
    public void updateSourceEntity(SourceDTO dto, SourceEntity entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getTitle() != null ) {
            entity.setTitle( dto.getTitle() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getReference() != null ) {
            entity.setReference( dto.getReference() );
        }
        if ( dto.getType() != null ) {
            entity.setType( dto.getType() );
        }
        if ( dto.getUrl() != null ) {
            entity.setUrl( dto.getUrl() );
        }
        if ( dto.getCreationYear() != null ) {
            entity.setCreationYear( dto.getCreationYear() );
        }
        if ( dto.getStartYear() != null ) {
            entity.setStartYear( dto.getStartYear() );
        }
        if ( dto.getEndYear() != null ) {
            entity.setEndYear( dto.getEndYear() );
        }
        if ( entity.getMetadata() != null ) {
            Map<String, Object> map = dto.getMetadata();
            if ( map != null ) {
                entity.getMetadata().clear();
                entity.getMetadata().putAll( map );
            }
        }
        else {
            Map<String, Object> map = dto.getMetadata();
            if ( map != null ) {
                entity.setMetadata( new LinkedHashMap<String, Object>( map ) );
            }
        }
        if ( dto.getCitationString() != null ) {
            entity.setCitationString( dto.getCitationString() );
        }
    }

    @Override
    public List<SourceDTO> toSourceDTOList(List<SourceEntity> sources) {
        if ( sources == null ) {
            return null;
        }

        List<SourceDTO> list = new ArrayList<SourceDTO>( sources.size() );
        for ( SourceEntity sourceEntity : sources ) {
            list.add( toSourceDTO( sourceEntity ) );
        }

        return list;
    }

    private Long sourceLocationId(SourceEntity sourceEntity) {
        if ( sourceEntity == null ) {
            return null;
        }
        LocationEntity location = sourceEntity.getLocation();
        if ( location == null ) {
            return null;
        }
        Long id = location.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String sourceLocationLocationName(SourceEntity sourceEntity) {
        if ( sourceEntity == null ) {
            return null;
        }
        LocationEntity location = sourceEntity.getLocation();
        if ( location == null ) {
            return null;
        }
        String locationName = location.getLocationName();
        if ( locationName == null ) {
            return null;
        }
        return locationName;
    }
}
