package cz.rodro.dto.mapper;

import cz.rodro.dto.ParishDTO;
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

        parishDTO.setId( parishEntity.getId() );
        parishDTO.setParishName( parishEntity.getParishName() );
        parishDTO.setParishMainChurchName( parishEntity.getParishMainChurchName() );
        parishDTO.setEstablishmentYear( parishEntity.getEstablishmentYear() );
        parishDTO.setCancellationYear( parishEntity.getCancellationYear() );

        return parishDTO;
    }

    @Override
    public ParishEntity toParishEntity(ParishDTO parishDTO) {
        if ( parishDTO == null ) {
            return null;
        }

        ParishEntity parishEntity = new ParishEntity();

        parishEntity.setId( parishDTO.getId() );
        parishEntity.setParishName( parishDTO.getParishName() );
        parishEntity.setParishMainChurchName( parishDTO.getParishMainChurchName() );
        parishEntity.setEstablishmentYear( parishDTO.getEstablishmentYear() );
        parishEntity.setCancellationYear( parishDTO.getCancellationYear() );

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
        parishEntity.setEstablishmentYear( parishDTO.getEstablishmentYear() );
        parishEntity.setCancellationYear( parishDTO.getCancellationYear() );
    }
}
