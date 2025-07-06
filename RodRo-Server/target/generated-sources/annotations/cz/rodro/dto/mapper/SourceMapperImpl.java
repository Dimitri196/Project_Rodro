package cz.rodro.dto.mapper;

import cz.rodro.dto.SourceDTO;
import cz.rodro.entity.SourceEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class SourceMapperImpl implements SourceMapper {

    @Override
    public SourceDTO toSourceDTO(SourceEntity entity) {
        if ( entity == null ) {
            return null;
        }

        SourceDTO sourceDTO = new SourceDTO();

        sourceDTO.setId( entity.getId() );
        sourceDTO.setSourceTitle( entity.getSourceTitle() );
        sourceDTO.setSourceDescription( entity.getSourceDescription() );
        sourceDTO.setSourceReference( entity.getSourceReference() );
        sourceDTO.setSourceType( entity.getSourceType() );
        sourceDTO.setSourceUrl( entity.getSourceUrl() );

        return sourceDTO;
    }

    @Override
    public SourceEntity toSourceEntity(SourceDTO dto) {
        if ( dto == null ) {
            return null;
        }

        SourceEntity sourceEntity = new SourceEntity();

        sourceEntity.setId( dto.getId() );
        sourceEntity.setSourceTitle( dto.getSourceTitle() );
        sourceEntity.setSourceDescription( dto.getSourceDescription() );
        sourceEntity.setSourceReference( dto.getSourceReference() );
        sourceEntity.setSourceType( dto.getSourceType() );
        sourceEntity.setSourceUrl( dto.getSourceUrl() );

        return sourceEntity;
    }

    @Override
    public void updateSourceEntity(SourceDTO dto, SourceEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        entity.setSourceTitle( dto.getSourceTitle() );
        entity.setSourceDescription( dto.getSourceDescription() );
        entity.setSourceReference( dto.getSourceReference() );
        entity.setSourceType( dto.getSourceType() );
        entity.setSourceUrl( dto.getSourceUrl() );
    }

    @Override
    public List<SourceDTO> toSourceDTOs(List<SourceEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<SourceDTO> list = new ArrayList<SourceDTO>( entities.size() );
        for ( SourceEntity sourceEntity : entities ) {
            list.add( toSourceDTO( sourceEntity ) );
        }

        return list;
    }

    @Override
    public List<SourceEntity> toSourceEntities(List<SourceDTO> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<SourceEntity> list = new ArrayList<SourceEntity>( dtos.size() );
        for ( SourceDTO sourceDTO : dtos ) {
            list.add( toSourceEntity( sourceDTO ) );
        }

        return list;
    }
}
