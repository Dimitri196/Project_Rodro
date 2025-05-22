package cz.rodro.dto.mapper;

import cz.rodro.dto.LocationDTO;
import cz.rodro.dto.PersonDTO;
import cz.rodro.dto.PersonOccupationDTO;
import cz.rodro.dto.PersonSourceEvidenceDTO;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.PersonEntity;
import cz.rodro.entity.PersonOccupationEntity;
import cz.rodro.entity.PersonSourceEvidenceEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class PersonMapperImpl implements PersonMapper {

    @Override
    public PersonEntity toEntity(PersonDTO source) {
        if ( source == null ) {
            return null;
        }

        PersonEntity personEntity = new PersonEntity();

        personEntity.setBirthPlace( locationDTOToLocationEntity( source.getBirthPlace() ) );
        personEntity.setDeathPlace( locationDTOToLocationEntity( source.getDeathPlace() ) );
        personEntity.setBurialPlace( locationDTOToLocationEntity( source.getBurialPlace() ) );
        personEntity.setBaptizationPlace( locationDTOToLocationEntity( source.getBaptizationPlace() ) );
        personEntity.setMother( toEntity( source.getMother() ) );
        personEntity.setFather( toEntity( source.getFather() ) );
        personEntity.setSocialStatus( source.getSocialStatus() );
        personEntity.setId( source.getId() );
        personEntity.setGivenName( source.getGivenName() );
        personEntity.setGivenSurname( source.getGivenSurname() );
        personEntity.setGender( source.getGender() );
        personEntity.setIdentificationNumber( source.getIdentificationNumber() );
        personEntity.setNote( source.getNote() );
        personEntity.setBirthDate( source.getBirthDate() );
        personEntity.setBaptizationDate( source.getBaptizationDate() );
        personEntity.setDeathDate( source.getDeathDate() );
        personEntity.setBurialDate( source.getBurialDate() );
        personEntity.setSourceEvidences( personSourceEvidenceDTOListToPersonSourceEvidenceEntityList( source.getSourceEvidences() ) );

        return personEntity;
    }

    @Override
    public PersonDTO toDTO(PersonEntity source) {
        if ( source == null ) {
            return null;
        }

        PersonDTO personDTO = new PersonDTO();

        personDTO.setOccupations( personOccupationEntityListToPersonOccupationDTOList( source.getOccupations() ) );
        personDTO.setSourceEvidences( personSourceEvidenceEntityListToPersonSourceEvidenceDTOList( source.getSourceEvidences() ) );
        personDTO.setId( source.getId() );
        personDTO.setGivenName( source.getGivenName() );
        personDTO.setGivenSurname( source.getGivenSurname() );
        personDTO.setGender( source.getGender() );
        personDTO.setIdentificationNumber( source.getIdentificationNumber() );
        personDTO.setNote( source.getNote() );
        personDTO.setBirthPlace( locationEntityToLocationDTO( source.getBirthPlace() ) );
        personDTO.setBaptizationPlace( locationEntityToLocationDTO( source.getBaptizationPlace() ) );
        personDTO.setDeathPlace( locationEntityToLocationDTO( source.getDeathPlace() ) );
        personDTO.setBurialPlace( locationEntityToLocationDTO( source.getBurialPlace() ) );
        personDTO.setMother( toDTO( source.getMother() ) );
        personDTO.setFather( toDTO( source.getFather() ) );
        personDTO.setSocialStatus( source.getSocialStatus() );
        personDTO.setBirthDate( source.getBirthDate() );
        personDTO.setBaptizationDate( source.getBaptizationDate() );
        personDTO.setDeathDate( source.getDeathDate() );
        personDTO.setBurialDate( source.getBurialDate() );

        return personDTO;
    }

    @Override
    public void updatePersonEntity(PersonDTO dto, PersonEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        entity.setGivenName( dto.getGivenName() );
        entity.setGivenSurname( dto.getGivenSurname() );
        entity.setGender( dto.getGender() );
        entity.setSocialStatus( dto.getSocialStatus() );
        entity.setIdentificationNumber( dto.getIdentificationNumber() );
        entity.setNote( dto.getNote() );
        if ( dto.getMother() != null ) {
            if ( entity.getMother() == null ) {
                entity.setMother( new PersonEntity() );
            }
            updatePersonEntity( dto.getMother(), entity.getMother() );
        }
        else {
            entity.setMother( null );
        }
        if ( dto.getFather() != null ) {
            if ( entity.getFather() == null ) {
                entity.setFather( new PersonEntity() );
            }
            updatePersonEntity( dto.getFather(), entity.getFather() );
        }
        else {
            entity.setFather( null );
        }
        entity.setBirthDate( dto.getBirthDate() );
        entity.setBaptizationDate( dto.getBaptizationDate() );
        entity.setDeathDate( dto.getDeathDate() );
        entity.setBurialDate( dto.getBurialDate() );
    }

    protected LocationEntity locationDTOToLocationEntity(LocationDTO locationDTO) {
        if ( locationDTO == null ) {
            return null;
        }

        LocationEntity locationEntity = new LocationEntity();

        locationEntity.setId( locationDTO.getId() );
        locationEntity.setLocationName( locationDTO.getLocationName() );
        locationEntity.setEstablishmentDate( locationDTO.getEstablishmentDate() );
        locationEntity.setGpsLatitude( locationDTO.getGpsLatitude() );
        locationEntity.setGpsLongitude( locationDTO.getGpsLongitude() );
        locationEntity.setSettlementType( locationDTO.getSettlementType() );

        return locationEntity;
    }

    protected PersonSourceEvidenceEntity personSourceEvidenceDTOToPersonSourceEvidenceEntity(PersonSourceEvidenceDTO personSourceEvidenceDTO) {
        if ( personSourceEvidenceDTO == null ) {
            return null;
        }

        PersonSourceEvidenceEntity personSourceEvidenceEntity = new PersonSourceEvidenceEntity();

        personSourceEvidenceEntity.setId( personSourceEvidenceDTO.getId() );
        personSourceEvidenceEntity.setPersonName( personSourceEvidenceDTO.getPersonName() );
        personSourceEvidenceEntity.setSourceName( personSourceEvidenceDTO.getSourceName() );

        return personSourceEvidenceEntity;
    }

    protected List<PersonSourceEvidenceEntity> personSourceEvidenceDTOListToPersonSourceEvidenceEntityList(List<PersonSourceEvidenceDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<PersonSourceEvidenceEntity> list1 = new ArrayList<PersonSourceEvidenceEntity>( list.size() );
        for ( PersonSourceEvidenceDTO personSourceEvidenceDTO : list ) {
            list1.add( personSourceEvidenceDTOToPersonSourceEvidenceEntity( personSourceEvidenceDTO ) );
        }

        return list1;
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

    protected LocationDTO locationEntityToLocationDTO(LocationEntity locationEntity) {
        if ( locationEntity == null ) {
            return null;
        }

        LocationDTO locationDTO = new LocationDTO();

        locationDTO.setId( locationEntity.getId() );
        locationDTO.setLocationName( locationEntity.getLocationName() );
        locationDTO.setEstablishmentDate( locationEntity.getEstablishmentDate() );
        locationDTO.setSettlementType( locationEntity.getSettlementType() );
        locationDTO.setGpsLatitude( locationEntity.getGpsLatitude() );
        locationDTO.setGpsLongitude( locationEntity.getGpsLongitude() );

        return locationDTO;
    }
}
