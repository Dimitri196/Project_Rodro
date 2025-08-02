package cz.rodro.dto.mapper;

import cz.rodro.dto.PersonDTO;
import cz.rodro.dto.PersonOccupationDTO;
import cz.rodro.dto.PersonSourceEvidenceDTO;
import cz.rodro.entity.PersonEntity;
import cz.rodro.entity.PersonOccupationEntity;
import cz.rodro.entity.PersonSourceEvidenceEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class PersonMapperImpl extends PersonMapper {

    @Autowired
    private LocationMapper locationMapper;

    @Override
    public PersonEntity toEntity(PersonDTO source) {
        if ( source == null ) {
            return null;
        }

        PersonEntity personEntity = new PersonEntity();

        personEntity.setId( source.getId() );
        personEntity.setGivenName( source.getGivenName() );
        personEntity.setGivenSurname( source.getGivenSurname() );
        personEntity.setGender( source.getGender() );
        personEntity.setSocialStatus( source.getSocialStatus() );
        personEntity.setCauseOfDeath( source.getCauseOfDeath() );
        personEntity.setIdentificationNumber( source.getIdentificationNumber() );
        personEntity.setNote( source.getNote() );
        personEntity.setBirthYear( source.getBirthYear() );
        personEntity.setBirthMonth( source.getBirthMonth() );
        personEntity.setBirthDay( source.getBirthDay() );
        personEntity.setBaptizationYear( source.getBaptizationYear() );
        personEntity.setBaptizationMonth( source.getBaptizationMonth() );
        personEntity.setBaptizationDay( source.getBaptizationDay() );
        personEntity.setDeathYear( source.getDeathYear() );
        personEntity.setDeathMonth( source.getDeathMonth() );
        personEntity.setDeathDay( source.getDeathDay() );
        personEntity.setBurialYear( source.getBurialYear() );
        personEntity.setBurialMonth( source.getBurialMonth() );
        personEntity.setBurialDay( source.getBurialDay() );

        return personEntity;
    }

    @Override
    public PersonDTO toDTO(PersonEntity source) {
        if ( source == null ) {
            return null;
        }

        PersonDTO personDTO = new PersonDTO();

        personDTO.setMotherId( sourceMotherId( source ) );
        personDTO.setFatherId( sourceFatherId( source ) );
        personDTO.setOccupations( personOccupationEntityListToPersonOccupationDTOList( source.getOccupations() ) );
        personDTO.setSourceEvidences( personSourceEvidenceEntityListToPersonSourceEvidenceDTOList( source.getSourceEvidences() ) );
        personDTO.setId( source.getId() );
        personDTO.setGivenName( source.getGivenName() );
        personDTO.setGivenSurname( source.getGivenSurname() );
        personDTO.setGender( source.getGender() );
        personDTO.setIdentificationNumber( source.getIdentificationNumber() );
        personDTO.setNote( source.getNote() );
        personDTO.setBirthPlace( locationMapper.toLocationDTO( source.getBirthPlace() ) );
        personDTO.setBaptizationPlace( locationMapper.toLocationDTO( source.getBaptizationPlace() ) );
        personDTO.setDeathPlace( locationMapper.toLocationDTO( source.getDeathPlace() ) );
        personDTO.setBurialPlace( locationMapper.toLocationDTO( source.getBurialPlace() ) );
        personDTO.setSocialStatus( source.getSocialStatus() );
        personDTO.setCauseOfDeath( source.getCauseOfDeath() );
        personDTO.setBirthYear( source.getBirthYear() );
        personDTO.setBirthMonth( source.getBirthMonth() );
        personDTO.setBirthDay( source.getBirthDay() );
        personDTO.setBaptizationYear( source.getBaptizationYear() );
        personDTO.setBaptizationMonth( source.getBaptizationMonth() );
        personDTO.setBaptizationDay( source.getBaptizationDay() );
        personDTO.setDeathYear( source.getDeathYear() );
        personDTO.setDeathMonth( source.getDeathMonth() );
        personDTO.setDeathDay( source.getDeathDay() );
        personDTO.setBurialYear( source.getBurialYear() );
        personDTO.setBurialMonth( source.getBurialMonth() );
        personDTO.setBurialDay( source.getBurialDay() );

        return personDTO;
    }

    @Override
    public void updatePersonEntity(PersonDTO dto, PersonEntity entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getGivenName() != null ) {
            entity.setGivenName( dto.getGivenName() );
        }
        if ( dto.getGivenSurname() != null ) {
            entity.setGivenSurname( dto.getGivenSurname() );
        }
        if ( dto.getGender() != null ) {
            entity.setGender( dto.getGender() );
        }
        if ( dto.getSocialStatus() != null ) {
            entity.setSocialStatus( dto.getSocialStatus() );
        }
        if ( dto.getCauseOfDeath() != null ) {
            entity.setCauseOfDeath( dto.getCauseOfDeath() );
        }
        if ( dto.getIdentificationNumber() != null ) {
            entity.setIdentificationNumber( dto.getIdentificationNumber() );
        }
        if ( dto.getNote() != null ) {
            entity.setNote( dto.getNote() );
        }
        if ( dto.getBirthYear() != null ) {
            entity.setBirthYear( dto.getBirthYear() );
        }
        if ( dto.getBirthMonth() != null ) {
            entity.setBirthMonth( dto.getBirthMonth() );
        }
        if ( dto.getBirthDay() != null ) {
            entity.setBirthDay( dto.getBirthDay() );
        }
        if ( dto.getBaptizationYear() != null ) {
            entity.setBaptizationYear( dto.getBaptizationYear() );
        }
        if ( dto.getBaptizationMonth() != null ) {
            entity.setBaptizationMonth( dto.getBaptizationMonth() );
        }
        if ( dto.getBaptizationDay() != null ) {
            entity.setBaptizationDay( dto.getBaptizationDay() );
        }
        if ( dto.getDeathYear() != null ) {
            entity.setDeathYear( dto.getDeathYear() );
        }
        if ( dto.getDeathMonth() != null ) {
            entity.setDeathMonth( dto.getDeathMonth() );
        }
        if ( dto.getDeathDay() != null ) {
            entity.setDeathDay( dto.getDeathDay() );
        }
        if ( dto.getBurialYear() != null ) {
            entity.setBurialYear( dto.getBurialYear() );
        }
        if ( dto.getBurialMonth() != null ) {
            entity.setBurialMonth( dto.getBurialMonth() );
        }
        if ( dto.getBurialDay() != null ) {
            entity.setBurialDay( dto.getBurialDay() );
        }
    }

    private Long sourceMotherId(PersonEntity personEntity) {
        if ( personEntity == null ) {
            return null;
        }
        PersonEntity mother = personEntity.getMother();
        if ( mother == null ) {
            return null;
        }
        Long id = mother.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long sourceFatherId(PersonEntity personEntity) {
        if ( personEntity == null ) {
            return null;
        }
        PersonEntity father = personEntity.getFather();
        if ( father == null ) {
            return null;
        }
        Long id = father.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected PersonOccupationDTO personOccupationEntityToPersonOccupationDTO(PersonOccupationEntity personOccupationEntity) {
        if ( personOccupationEntity == null ) {
            return null;
        }

        PersonOccupationDTO personOccupationDTO = new PersonOccupationDTO();

        personOccupationDTO.setId( personOccupationEntity.getId() );

        return personOccupationDTO;
    }

    protected List<PersonOccupationDTO> personOccupationEntityListToPersonOccupationDTOList(List<PersonOccupationEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<PersonOccupationDTO> list1 = new ArrayList<PersonOccupationDTO>( list.size() );
        for ( PersonOccupationEntity personOccupationEntity : list ) {
            list1.add( personOccupationEntityToPersonOccupationDTO( personOccupationEntity ) );
        }

        return list1;
    }

    protected PersonSourceEvidenceDTO personSourceEvidenceEntityToPersonSourceEvidenceDTO(PersonSourceEvidenceEntity personSourceEvidenceEntity) {
        if ( personSourceEvidenceEntity == null ) {
            return null;
        }

        PersonSourceEvidenceDTO personSourceEvidenceDTO = new PersonSourceEvidenceDTO();

        personSourceEvidenceDTO.setId( personSourceEvidenceEntity.getId() );
        personSourceEvidenceDTO.setPersonName( personSourceEvidenceEntity.getPersonName() );
        personSourceEvidenceDTO.setSourceName( personSourceEvidenceEntity.getSourceName() );

        return personSourceEvidenceDTO;
    }

    protected List<PersonSourceEvidenceDTO> personSourceEvidenceEntityListToPersonSourceEvidenceDTOList(List<PersonSourceEvidenceEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<PersonSourceEvidenceDTO> list1 = new ArrayList<PersonSourceEvidenceDTO>( list.size() );
        for ( PersonSourceEvidenceEntity personSourceEvidenceEntity : list ) {
            list1.add( personSourceEvidenceEntityToPersonSourceEvidenceDTO( personSourceEvidenceEntity ) );
        }

        return list1;
    }
}
