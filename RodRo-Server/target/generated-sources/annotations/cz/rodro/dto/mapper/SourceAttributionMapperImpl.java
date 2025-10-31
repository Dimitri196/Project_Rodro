package cz.rodro.dto.mapper;

import cz.rodro.dto.SourceAttributionDTO;
import cz.rodro.entity.FamilyEntity;
import cz.rodro.entity.PersonEntity;
import cz.rodro.entity.PersonMilitaryServiceEntity;
import cz.rodro.entity.PersonOccupationEntity;
import cz.rodro.entity.SourceAttributionEntity;
import cz.rodro.entity.SourceEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class SourceAttributionMapperImpl implements SourceAttributionMapper {

    @Override
    public SourceAttributionDTO toDTO(SourceAttributionEntity entity) {
        if ( entity == null ) {
            return null;
        }

        SourceAttributionDTO.SourceAttributionDTOBuilder sourceAttributionDTO = SourceAttributionDTO.builder();

        sourceAttributionDTO.sourceId( entitySourceId( entity ) );
        sourceAttributionDTO.personId( entityPersonId( entity ) );
        sourceAttributionDTO.occupationId( entityOccupationId( entity ) );
        sourceAttributionDTO.familyId( entityFamilyId( entity ) );
        sourceAttributionDTO.militaryServiceId( entityMilitaryServiceId( entity ) );
        sourceAttributionDTO.id( entity.getId() );
        sourceAttributionDTO.type( entity.getType() );
        sourceAttributionDTO.note( entity.getNote() );

        return sourceAttributionDTO.build();
    }

    @Override
    public SourceAttributionEntity toEntity(SourceAttributionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        SourceAttributionEntity.SourceAttributionEntityBuilder sourceAttributionEntity = SourceAttributionEntity.builder();

        sourceAttributionEntity.id( dto.getId() );
        sourceAttributionEntity.type( dto.getType() );
        sourceAttributionEntity.note( dto.getNote() );

        return sourceAttributionEntity.build();
    }

    private Long entitySourceId(SourceAttributionEntity sourceAttributionEntity) {
        if ( sourceAttributionEntity == null ) {
            return null;
        }
        SourceEntity source = sourceAttributionEntity.getSource();
        if ( source == null ) {
            return null;
        }
        Long id = source.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityPersonId(SourceAttributionEntity sourceAttributionEntity) {
        if ( sourceAttributionEntity == null ) {
            return null;
        }
        PersonEntity person = sourceAttributionEntity.getPerson();
        if ( person == null ) {
            return null;
        }
        Long id = person.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityOccupationId(SourceAttributionEntity sourceAttributionEntity) {
        if ( sourceAttributionEntity == null ) {
            return null;
        }
        PersonOccupationEntity occupation = sourceAttributionEntity.getOccupation();
        if ( occupation == null ) {
            return null;
        }
        Long id = occupation.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityFamilyId(SourceAttributionEntity sourceAttributionEntity) {
        if ( sourceAttributionEntity == null ) {
            return null;
        }
        FamilyEntity family = sourceAttributionEntity.getFamily();
        if ( family == null ) {
            return null;
        }
        Long id = family.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityMilitaryServiceId(SourceAttributionEntity sourceAttributionEntity) {
        if ( sourceAttributionEntity == null ) {
            return null;
        }
        PersonMilitaryServiceEntity militaryService = sourceAttributionEntity.getMilitaryService();
        if ( militaryService == null ) {
            return null;
        }
        Long id = militaryService.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
