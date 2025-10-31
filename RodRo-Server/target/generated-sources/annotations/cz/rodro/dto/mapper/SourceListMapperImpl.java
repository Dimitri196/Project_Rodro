package cz.rodro.dto.mapper;

import cz.rodro.dto.SourceListDTO;
import cz.rodro.dto.SourceListProjection;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class SourceListMapperImpl implements SourceListMapper {

    @Override
    public SourceListDTO toDTO(SourceListProjection projection) {
        if ( projection == null ) {
            return null;
        }

        SourceListDTO sourceListDTO = new SourceListDTO();

        sourceListDTO.setId( projection.getId() );
        sourceListDTO.setTitle( projection.getTitle() );
        sourceListDTO.setReference( projection.getReference() );
        sourceListDTO.setDescription( projection.getDescription() );
        sourceListDTO.setUrl( projection.getUrl() );
        sourceListDTO.setType( mapSourceTypeToString( projection.getType() ) );
        sourceListDTO.setCreationYear( projection.getCreationYear() );
        sourceListDTO.setStartYear( projection.getStartYear() );
        sourceListDTO.setEndYear( projection.getEndYear() );
        sourceListDTO.setLocationId( projection.getLocationId() );
        sourceListDTO.setLocationName( projection.getLocationName() );

        return sourceListDTO;
    }
}
