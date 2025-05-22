package cz.rodro.dto.mapper;

import cz.rodro.dto.FamilyDTO;
import cz.rodro.dto.LocationDTO;
import cz.rodro.dto.PersonDTO;
import cz.rodro.dto.PersonOccupationDTO;
import cz.rodro.dto.PersonSourceEvidenceDTO;
import cz.rodro.entity.FamilyEntity;
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
public class FamilyMapperImpl implements FamilyMapper {

    @Override
    public FamilyEntity toFamilyEntity(FamilyDTO source) {
        if ( source == null ) {
            return null;
        }

        FamilyEntity familyEntity = new FamilyEntity();

        familyEntity.setId( source.getId() );
        familyEntity.setMarriageDate( source.getMarriageDate() );
        familyEntity.setMarriageLocation( locationDTOToLocationEntity( source.getMarriageLocation() ) );
        familyEntity.setSpouseMale( personDTOToPersonEntity( source.getSpouseMale() ) );
        familyEntity.setMaritalStatusForSpouseMale( source.getMaritalStatusForSpouseMale() );
        familyEntity.setSpouseFemale( personDTOToPersonEntity( source.getSpouseFemale() ) );
        familyEntity.setMaritalStatusForSpouseFemale( source.getMaritalStatusForSpouseFemale() );
        familyEntity.setWitnessesMaleSide1( personDTOToPersonEntity( source.getWitnessesMaleSide1() ) );
        familyEntity.setWitnessesMaleSide2( personDTOToPersonEntity( source.getWitnessesMaleSide2() ) );
        familyEntity.setWitnessesFemaleSide1( personDTOToPersonEntity( source.getWitnessesFemaleSide1() ) );
        familyEntity.setWitnessesFemaleSide2( personDTOToPersonEntity( source.getWitnessesFemaleSide2() ) );
        familyEntity.setSource( source.getSource() );
        familyEntity.setNote( source.getNote() );

        return familyEntity;
    }

    @Override
    public FamilyDTO toFamilyDTO(FamilyEntity source) {
        if ( source == null ) {
            return null;
        }

        FamilyDTO familyDTO = new FamilyDTO();

        familyDTO.setId( source.getId() );
        familyDTO.setMarriageDate( source.getMarriageDate() );
        familyDTO.setMarriageLocation( locationEntityToLocationDTO( source.getMarriageLocation() ) );
        familyDTO.setSpouseMale( personEntityToPersonDTO( source.getSpouseMale() ) );
        familyDTO.setMaritalStatusForSpouseMale( source.getMaritalStatusForSpouseMale() );
        familyDTO.setSpouseFemale( personEntityToPersonDTO( source.getSpouseFemale() ) );
        familyDTO.setMaritalStatusForSpouseFemale( source.getMaritalStatusForSpouseFemale() );
        familyDTO.setWitnessesMaleSide1( personEntityToPersonDTO( source.getWitnessesMaleSide1() ) );
        familyDTO.setWitnessesMaleSide2( personEntityToPersonDTO( source.getWitnessesMaleSide2() ) );
        familyDTO.setWitnessesFemaleSide1( personEntityToPersonDTO( source.getWitnessesFemaleSide1() ) );
        familyDTO.setWitnessesFemaleSide2( personEntityToPersonDTO( source.getWitnessesFemaleSide2() ) );
        familyDTO.setSource( source.getSource() );
        familyDTO.setNote( source.getNote() );

        return familyDTO;
    }

    @Override
    public void updateFamilyEntity(FamilyDTO source, FamilyEntity target) {
        if ( source == null ) {
            return;
        }

        target.setId( source.getId() );
        target.setMarriageDate( source.getMarriageDate() );
        target.setMaritalStatusForSpouseMale( source.getMaritalStatusForSpouseMale() );
        target.setMaritalStatusForSpouseFemale( source.getMaritalStatusForSpouseFemale() );
        if ( source.getWitnessesMaleSide1() != null ) {
            if ( target.getWitnessesMaleSide1() == null ) {
                target.setWitnessesMaleSide1( new PersonEntity() );
            }
            personDTOToPersonEntity1( source.getWitnessesMaleSide1(), target.getWitnessesMaleSide1() );
        }
        else {
            target.setWitnessesMaleSide1( null );
        }
        if ( source.getWitnessesMaleSide2() != null ) {
            if ( target.getWitnessesMaleSide2() == null ) {
                target.setWitnessesMaleSide2( new PersonEntity() );
            }
            personDTOToPersonEntity1( source.getWitnessesMaleSide2(), target.getWitnessesMaleSide2() );
        }
        else {
            target.setWitnessesMaleSide2( null );
        }
        if ( source.getWitnessesFemaleSide1() != null ) {
            if ( target.getWitnessesFemaleSide1() == null ) {
                target.setWitnessesFemaleSide1( new PersonEntity() );
            }
            personDTOToPersonEntity1( source.getWitnessesFemaleSide1(), target.getWitnessesFemaleSide1() );
        }
        else {
            target.setWitnessesFemaleSide1( null );
        }
        if ( source.getWitnessesFemaleSide2() != null ) {
            if ( target.getWitnessesFemaleSide2() == null ) {
                target.setWitnessesFemaleSide2( new PersonEntity() );
            }
            personDTOToPersonEntity1( source.getWitnessesFemaleSide2(), target.getWitnessesFemaleSide2() );
        }
        else {
            target.setWitnessesFemaleSide2( null );
        }
        target.setSource( source.getSource() );
        target.setNote( source.getNote() );
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

    protected PersonOccupationEntity personOccupationDTOToPersonOccupationEntity(PersonOccupationDTO personOccupationDTO) {
        if ( personOccupationDTO == null ) {
            return null;
        }

        PersonOccupationEntity personOccupationEntity = new PersonOccupationEntity();

        personOccupationEntity.setId( personOccupationDTO.getId() );

        return personOccupationEntity;
    }

    protected List<PersonOccupationEntity> personOccupationDTOListToPersonOccupationEntityList(List<PersonOccupationDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<PersonOccupationEntity> list1 = new ArrayList<PersonOccupationEntity>( list.size() );
        for ( PersonOccupationDTO personOccupationDTO : list ) {
            list1.add( personOccupationDTOToPersonOccupationEntity( personOccupationDTO ) );
        }

        return list1;
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

    protected PersonEntity personDTOToPersonEntity(PersonDTO personDTO) {
        if ( personDTO == null ) {
            return null;
        }

        PersonEntity personEntity = new PersonEntity();

        personEntity.setId( personDTO.getId() );
        personEntity.setGivenName( personDTO.getGivenName() );
        personEntity.setGivenSurname( personDTO.getGivenSurname() );
        personEntity.setGender( personDTO.getGender() );
        personEntity.setSocialStatus( personDTO.getSocialStatus() );
        personEntity.setIdentificationNumber( personDTO.getIdentificationNumber() );
        personEntity.setNote( personDTO.getNote() );
        personEntity.setBirthPlace( locationDTOToLocationEntity( personDTO.getBirthPlace() ) );
        personEntity.setDeathPlace( locationDTOToLocationEntity( personDTO.getDeathPlace() ) );
        personEntity.setBurialPlace( locationDTOToLocationEntity( personDTO.getBurialPlace() ) );
        personEntity.setBaptizationPlace( locationDTOToLocationEntity( personDTO.getBaptizationPlace() ) );
        personEntity.setMother( personDTOToPersonEntity( personDTO.getMother() ) );
        personEntity.setFather( personDTOToPersonEntity( personDTO.getFather() ) );
        personEntity.setBirthDate( personDTO.getBirthDate() );
        personEntity.setBaptizationDate( personDTO.getBaptizationDate() );
        personEntity.setDeathDate( personDTO.getDeathDate() );
        personEntity.setBurialDate( personDTO.getBurialDate() );
        personEntity.setOccupations( personOccupationDTOListToPersonOccupationEntityList( personDTO.getOccupations() ) );
        personEntity.setSourceEvidences( personSourceEvidenceDTOListToPersonSourceEvidenceEntityList( personDTO.getSourceEvidences() ) );

        return personEntity;
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

    protected PersonDTO personEntityToPersonDTO(PersonEntity personEntity) {
        if ( personEntity == null ) {
            return null;
        }

        PersonDTO personDTO = new PersonDTO();

        personDTO.setId( personEntity.getId() );
        personDTO.setGivenName( personEntity.getGivenName() );
        personDTO.setGivenSurname( personEntity.getGivenSurname() );
        personDTO.setGender( personEntity.getGender() );
        personDTO.setIdentificationNumber( personEntity.getIdentificationNumber() );
        personDTO.setNote( personEntity.getNote() );
        personDTO.setBirthPlace( locationEntityToLocationDTO( personEntity.getBirthPlace() ) );
        personDTO.setBaptizationPlace( locationEntityToLocationDTO( personEntity.getBaptizationPlace() ) );
        personDTO.setDeathPlace( locationEntityToLocationDTO( personEntity.getDeathPlace() ) );
        personDTO.setBurialPlace( locationEntityToLocationDTO( personEntity.getBurialPlace() ) );
        personDTO.setMother( personEntityToPersonDTO( personEntity.getMother() ) );
        personDTO.setFather( personEntityToPersonDTO( personEntity.getFather() ) );
        personDTO.setSocialStatus( personEntity.getSocialStatus() );
        personDTO.setOccupations( personOccupationEntityListToPersonOccupationDTOList( personEntity.getOccupations() ) );
        personDTO.setBirthDate( personEntity.getBirthDate() );
        personDTO.setBaptizationDate( personEntity.getBaptizationDate() );
        personDTO.setDeathDate( personEntity.getDeathDate() );
        personDTO.setBurialDate( personEntity.getBurialDate() );
        personDTO.setSourceEvidences( personSourceEvidenceEntityListToPersonSourceEvidenceDTOList( personEntity.getSourceEvidences() ) );

        return personDTO;
    }

    protected void locationDTOToLocationEntity1(LocationDTO locationDTO, LocationEntity mappingTarget) {
        if ( locationDTO == null ) {
            return;
        }

        mappingTarget.setId( locationDTO.getId() );
        mappingTarget.setLocationName( locationDTO.getLocationName() );
        mappingTarget.setEstablishmentDate( locationDTO.getEstablishmentDate() );
        mappingTarget.setGpsLatitude( locationDTO.getGpsLatitude() );
        mappingTarget.setGpsLongitude( locationDTO.getGpsLongitude() );
        mappingTarget.setSettlementType( locationDTO.getSettlementType() );
    }

    protected void personDTOToPersonEntity1(PersonDTO personDTO, PersonEntity mappingTarget) {
        if ( personDTO == null ) {
            return;
        }

        mappingTarget.setId( personDTO.getId() );
        mappingTarget.setGivenName( personDTO.getGivenName() );
        mappingTarget.setGivenSurname( personDTO.getGivenSurname() );
        mappingTarget.setGender( personDTO.getGender() );
        mappingTarget.setSocialStatus( personDTO.getSocialStatus() );
        mappingTarget.setIdentificationNumber( personDTO.getIdentificationNumber() );
        mappingTarget.setNote( personDTO.getNote() );
        if ( personDTO.getBirthPlace() != null ) {
            if ( mappingTarget.getBirthPlace() == null ) {
                mappingTarget.setBirthPlace( new LocationEntity() );
            }
            locationDTOToLocationEntity1( personDTO.getBirthPlace(), mappingTarget.getBirthPlace() );
        }
        else {
            mappingTarget.setBirthPlace( null );
        }
        if ( personDTO.getDeathPlace() != null ) {
            if ( mappingTarget.getDeathPlace() == null ) {
                mappingTarget.setDeathPlace( new LocationEntity() );
            }
            locationDTOToLocationEntity1( personDTO.getDeathPlace(), mappingTarget.getDeathPlace() );
        }
        else {
            mappingTarget.setDeathPlace( null );
        }
        if ( personDTO.getBurialPlace() != null ) {
            if ( mappingTarget.getBurialPlace() == null ) {
                mappingTarget.setBurialPlace( new LocationEntity() );
            }
            locationDTOToLocationEntity1( personDTO.getBurialPlace(), mappingTarget.getBurialPlace() );
        }
        else {
            mappingTarget.setBurialPlace( null );
        }
        if ( personDTO.getBaptizationPlace() != null ) {
            if ( mappingTarget.getBaptizationPlace() == null ) {
                mappingTarget.setBaptizationPlace( new LocationEntity() );
            }
            locationDTOToLocationEntity1( personDTO.getBaptizationPlace(), mappingTarget.getBaptizationPlace() );
        }
        else {
            mappingTarget.setBaptizationPlace( null );
        }
        if ( personDTO.getMother() != null ) {
            if ( mappingTarget.getMother() == null ) {
                mappingTarget.setMother( new PersonEntity() );
            }
            personDTOToPersonEntity1( personDTO.getMother(), mappingTarget.getMother() );
        }
        else {
            mappingTarget.setMother( null );
        }
        if ( personDTO.getFather() != null ) {
            if ( mappingTarget.getFather() == null ) {
                mappingTarget.setFather( new PersonEntity() );
            }
            personDTOToPersonEntity1( personDTO.getFather(), mappingTarget.getFather() );
        }
        else {
            mappingTarget.setFather( null );
        }
        mappingTarget.setBirthDate( personDTO.getBirthDate() );
        mappingTarget.setBaptizationDate( personDTO.getBaptizationDate() );
        mappingTarget.setDeathDate( personDTO.getDeathDate() );
        mappingTarget.setBurialDate( personDTO.getBurialDate() );
        if ( mappingTarget.getOccupations() != null ) {
            List<PersonOccupationEntity> list = personOccupationDTOListToPersonOccupationEntityList( personDTO.getOccupations() );
            if ( list != null ) {
                mappingTarget.getOccupations().clear();
                mappingTarget.getOccupations().addAll( list );
            }
            else {
                mappingTarget.setOccupations( null );
            }
        }
        else {
            List<PersonOccupationEntity> list = personOccupationDTOListToPersonOccupationEntityList( personDTO.getOccupations() );
            if ( list != null ) {
                mappingTarget.setOccupations( list );
            }
        }
        if ( mappingTarget.getSourceEvidences() != null ) {
            List<PersonSourceEvidenceEntity> list1 = personSourceEvidenceDTOListToPersonSourceEvidenceEntityList( personDTO.getSourceEvidences() );
            if ( list1 != null ) {
                mappingTarget.getSourceEvidences().clear();
                mappingTarget.getSourceEvidences().addAll( list1 );
            }
            else {
                mappingTarget.setSourceEvidences( null );
            }
        }
        else {
            List<PersonSourceEvidenceEntity> list1 = personSourceEvidenceDTOListToPersonSourceEvidenceEntityList( personDTO.getSourceEvidences() );
            if ( list1 != null ) {
                mappingTarget.setSourceEvidences( list1 );
            }
        }
    }
}
