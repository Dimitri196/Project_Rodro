package cz.rodro.dto.mapper;

import cz.rodro.dto.PersonOccupationDTO;
import cz.rodro.entity.InstitutionEntity;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.OccupationEntity;
import cz.rodro.entity.PersonEntity;
import cz.rodro.entity.PersonOccupationEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class PersonOccupationMapperImpl implements PersonOccupationMapper {

    @Override
    public PersonOccupationDTO toDTO(PersonOccupationEntity entity) {
        if ( entity == null ) {
            return null;
        }

        PersonOccupationDTO personOccupationDTO = new PersonOccupationDTO();

        personOccupationDTO.setPersonId( entityPersonId( entity ) );
        personOccupationDTO.setGivenName( entityPersonGivenName( entity ) );
        personOccupationDTO.setSurname( entityPersonSurname( entity ) );
        personOccupationDTO.setOccupationId( entityOccupationId( entity ) );
        personOccupationDTO.setStartYear( entity.getStartYear() );
        personOccupationDTO.setEndYear( entity.getEndYear() );
        personOccupationDTO.setOccupationName( entityOccupationOccupationName( entity ) );
        personOccupationDTO.setInstitutionId( entityOccupationInstitutionId( entity ) );
        personOccupationDTO.setInstitutionName( entityOccupationInstitutionInstitutionName( entity ) );
        personOccupationDTO.setInstitutionLocationId( entityOccupationInstitutionInstitutionLocationId( entity ) );
        personOccupationDTO.setInstitutionLocationName( entityOccupationInstitutionInstitutionLocationLocationName( entity ) );
        personOccupationDTO.setId( entity.getId() );

        return personOccupationDTO;
    }

    @Override
    public PersonOccupationEntity toEntity(PersonOccupationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PersonOccupationEntity personOccupationEntity = new PersonOccupationEntity();

        personOccupationEntity.setId( dto.getId() );
        personOccupationEntity.setStartYear( dto.getStartYear() );
        personOccupationEntity.setEndYear( dto.getEndYear() );

        return personOccupationEntity;
    }

    @Override
    public void updatePersonOccupationEntity(PersonOccupationDTO dto, PersonOccupationEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setStartYear( dto.getStartYear() );
        entity.setEndYear( dto.getEndYear() );
    }

    private Long entityPersonId(PersonOccupationEntity personOccupationEntity) {
        if ( personOccupationEntity == null ) {
            return null;
        }
        PersonEntity person = personOccupationEntity.getPerson();
        if ( person == null ) {
            return null;
        }
        Long id = person.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityPersonGivenName(PersonOccupationEntity personOccupationEntity) {
        if ( personOccupationEntity == null ) {
            return null;
        }
        PersonEntity person = personOccupationEntity.getPerson();
        if ( person == null ) {
            return null;
        }
        String givenName = person.getGivenName();
        if ( givenName == null ) {
            return null;
        }
        return givenName;
    }

    private String entityPersonSurname(PersonOccupationEntity personOccupationEntity) {
        if ( personOccupationEntity == null ) {
            return null;
        }
        PersonEntity person = personOccupationEntity.getPerson();
        if ( person == null ) {
            return null;
        }
        String surname = person.getSurname();
        if ( surname == null ) {
            return null;
        }
        return surname;
    }

    private Long entityOccupationId(PersonOccupationEntity personOccupationEntity) {
        if ( personOccupationEntity == null ) {
            return null;
        }
        OccupationEntity occupation = personOccupationEntity.getOccupation();
        if ( occupation == null ) {
            return null;
        }
        Long id = occupation.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityOccupationOccupationName(PersonOccupationEntity personOccupationEntity) {
        if ( personOccupationEntity == null ) {
            return null;
        }
        OccupationEntity occupation = personOccupationEntity.getOccupation();
        if ( occupation == null ) {
            return null;
        }
        String occupationName = occupation.getOccupationName();
        if ( occupationName == null ) {
            return null;
        }
        return occupationName;
    }

    private Long entityOccupationInstitutionId(PersonOccupationEntity personOccupationEntity) {
        if ( personOccupationEntity == null ) {
            return null;
        }
        OccupationEntity occupation = personOccupationEntity.getOccupation();
        if ( occupation == null ) {
            return null;
        }
        InstitutionEntity institution = occupation.getInstitution();
        if ( institution == null ) {
            return null;
        }
        Long id = institution.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityOccupationInstitutionInstitutionName(PersonOccupationEntity personOccupationEntity) {
        if ( personOccupationEntity == null ) {
            return null;
        }
        OccupationEntity occupation = personOccupationEntity.getOccupation();
        if ( occupation == null ) {
            return null;
        }
        InstitutionEntity institution = occupation.getInstitution();
        if ( institution == null ) {
            return null;
        }
        String institutionName = institution.getInstitutionName();
        if ( institutionName == null ) {
            return null;
        }
        return institutionName;
    }

    private Long entityOccupationInstitutionInstitutionLocationId(PersonOccupationEntity personOccupationEntity) {
        if ( personOccupationEntity == null ) {
            return null;
        }
        OccupationEntity occupation = personOccupationEntity.getOccupation();
        if ( occupation == null ) {
            return null;
        }
        InstitutionEntity institution = occupation.getInstitution();
        if ( institution == null ) {
            return null;
        }
        LocationEntity institutionLocation = institution.getInstitutionLocation();
        if ( institutionLocation == null ) {
            return null;
        }
        Long id = institutionLocation.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityOccupationInstitutionInstitutionLocationLocationName(PersonOccupationEntity personOccupationEntity) {
        if ( personOccupationEntity == null ) {
            return null;
        }
        OccupationEntity occupation = personOccupationEntity.getOccupation();
        if ( occupation == null ) {
            return null;
        }
        InstitutionEntity institution = occupation.getInstitution();
        if ( institution == null ) {
            return null;
        }
        LocationEntity institutionLocation = institution.getInstitutionLocation();
        if ( institutionLocation == null ) {
            return null;
        }
        String locationName = institutionLocation.getLocationName();
        if ( locationName == null ) {
            return null;
        }
        return locationName;
    }
}
