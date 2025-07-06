package cz.rodro.dto.mapper;

import cz.rodro.dto.PersonSourceEvidenceDTO;
import cz.rodro.entity.PersonEntity;
import cz.rodro.entity.PersonSourceEvidenceEntity;
import cz.rodro.entity.SourceEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class PersonSourceEvidenceMapperImpl implements PersonSourceEvidenceMapper {

    @Override
    public PersonSourceEvidenceDTO toDTO(PersonSourceEvidenceEntity entity) {
        if ( entity == null ) {
            return null;
        }

        PersonSourceEvidenceDTO personSourceEvidenceDTO = new PersonSourceEvidenceDTO();

        personSourceEvidenceDTO.setPersonId( entityPersonId( entity ) );
        personSourceEvidenceDTO.setSourceId( entitySourceId( entity ) );
        personSourceEvidenceDTO.setPersonName( entity.getPersonName() );
        personSourceEvidenceDTO.setSourceName( entity.getSourceName() );
        personSourceEvidenceDTO.setId( entity.getId() );

        return personSourceEvidenceDTO;
    }

    @Override
    public PersonSourceEvidenceEntity toEntity(PersonSourceEvidenceDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PersonSourceEvidenceEntity personSourceEvidenceEntity = new PersonSourceEvidenceEntity();

        personSourceEvidenceEntity.setId( dto.getId() );
        personSourceEvidenceEntity.setPersonName( dto.getPersonName() );
        personSourceEvidenceEntity.setSourceName( dto.getSourceName() );

        return personSourceEvidenceEntity;
    }

    @Override
    public void updatePersonSourceEvidenceEntity(PersonSourceEvidenceDTO dto, PersonSourceEvidenceEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        entity.setPersonName( dto.getPersonName() );
        entity.setSourceName( dto.getSourceName() );
    }

    private Long entityPersonId(PersonSourceEvidenceEntity personSourceEvidenceEntity) {
        if ( personSourceEvidenceEntity == null ) {
            return null;
        }
        PersonEntity person = personSourceEvidenceEntity.getPerson();
        if ( person == null ) {
            return null;
        }
        Long id = person.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entitySourceId(PersonSourceEvidenceEntity personSourceEvidenceEntity) {
        if ( personSourceEvidenceEntity == null ) {
            return null;
        }
        SourceEntity source = personSourceEvidenceEntity.getSource();
        if ( source == null ) {
            return null;
        }
        Long id = source.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
