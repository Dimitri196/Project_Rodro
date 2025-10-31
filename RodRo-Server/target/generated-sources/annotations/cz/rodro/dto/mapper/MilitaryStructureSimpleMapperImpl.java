package cz.rodro.dto.mapper;

import cz.rodro.dto.MilitaryStructureSimpleDTO;
import cz.rodro.entity.MilitaryStructureEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class MilitaryStructureSimpleMapperImpl implements MilitaryStructureSimpleMapper {

    @Override
    public MilitaryStructureSimpleDTO toMilitaryStructureSimpleDTO(MilitaryStructureEntity entity) {
        if ( entity == null ) {
            return null;
        }

        MilitaryStructureSimpleDTO militaryStructureSimpleDTO = new MilitaryStructureSimpleDTO();

        militaryStructureSimpleDTO.setId( entity.getId() );
        militaryStructureSimpleDTO.setUnitName( entity.getUnitName() );

        return militaryStructureSimpleDTO;
    }
}
