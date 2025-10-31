package cz.rodro.dto.mapper;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class PersonMapperImpl implements PersonMapper {

    @Autowired
    private LocationMapper locationMapper;
    @Autowired
    private PersonOccupationMapper personOccupationMapper;
    @Autowired
    private PersonSourceEvidenceMapper personSourceEvidenceMapper;

    @Override
    public PersonDTO toDTO(PersonEntity source) {
        if ( source == null ) {
            return null;
        }

        PersonDTO personDTO = new PersonDTO();

        personDTO.setMotherId( sourceMotherId( source ) );
        personDTO.setFatherId( sourceFatherId( source ) );
        personDTO.setId( source.getId() );
        personDTO.setGivenName( source.getGivenName() );
        personDTO.setSurname( source.getSurname() );
        personDTO.setExternalId( source.getExternalId() );
        personDTO.setGender( source.getGender() );
        personDTO.setBioNote( source.getBioNote() );
        personDTO.setBirthPlace( locationMapper.toLocationDTO( source.getBirthPlace() ) );
        personDTO.setBaptismPlace( locationMapper.toLocationDTO( source.getBaptismPlace() ) );
        personDTO.setDeathPlace( locationMapper.toLocationDTO( source.getDeathPlace() ) );
        personDTO.setBurialPlace( locationMapper.toLocationDTO( source.getBurialPlace() ) );
        personDTO.setSocialStatus( source.getSocialStatus() );
        personDTO.setCauseOfDeath( source.getCauseOfDeath() );
        personDTO.setOccupations( personOccupationEntityListToPersonOccupationDTOList( source.getOccupations() ) );
        personDTO.setSourceEvidences( personSourceEvidenceEntityListToPersonSourceEvidenceDTOList( source.getSourceEvidences() ) );
        personDTO.setBirthYear( source.getBirthYear() );
        personDTO.setBirthMonth( source.getBirthMonth() );
        personDTO.setBirthDay( source.getBirthDay() );
        personDTO.setBaptismYear( source.getBaptismYear() );
        personDTO.setBaptismMonth( source.getBaptismMonth() );
        personDTO.setBaptismDay( source.getBaptismDay() );
        personDTO.setDeathYear( source.getDeathYear() );
        personDTO.setDeathMonth( source.getDeathMonth() );
        personDTO.setDeathDay( source.getDeathDay() );
        personDTO.setBurialYear( source.getBurialYear() );
        personDTO.setBurialMonth( source.getBurialMonth() );
        personDTO.setBurialDay( source.getBurialDay() );

        return personDTO;
    }

    @Override
    public List<PersonDTO> toDTOList(List<PersonEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<PersonDTO> list = new ArrayList<PersonDTO>( entities.size() );
        for ( PersonEntity personEntity : entities ) {
            list.add( toDTO( personEntity ) );
        }

        return list;
    }

    @Override
    public PersonEntity toEntity(PersonDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PersonEntity personEntity = new PersonEntity();

        personEntity.setOccupations( personOccupationDTOListToPersonOccupationEntityList( dto.getOccupations() ) );
        personEntity.setSourceEvidences( personSourceEvidenceDTOListToPersonSourceEvidenceEntityList( dto.getSourceEvidences() ) );
        personEntity.setId( dto.getId() );
        personEntity.setGivenName( dto.getGivenName() );
        personEntity.setSurname( dto.getSurname() );
        personEntity.setGender( dto.getGender() );
        personEntity.setSocialStatus( dto.getSocialStatus() );
        personEntity.setCauseOfDeath( dto.getCauseOfDeath() );
        personEntity.setExternalId( dto.getExternalId() );
        personEntity.setBioNote( dto.getBioNote() );
        personEntity.setBirthPlace( locationMapper.toLocationEntity( dto.getBirthPlace() ) );
        personEntity.setDeathPlace( locationMapper.toLocationEntity( dto.getDeathPlace() ) );
        personEntity.setBurialPlace( locationMapper.toLocationEntity( dto.getBurialPlace() ) );
        personEntity.setBaptismPlace( locationMapper.toLocationEntity( dto.getBaptismPlace() ) );
        personEntity.setBirthYear( dto.getBirthYear() );
        personEntity.setBirthMonth( dto.getBirthMonth() );
        personEntity.setBirthDay( dto.getBirthDay() );
        personEntity.setBaptismYear( dto.getBaptismYear() );
        personEntity.setBaptismMonth( dto.getBaptismMonth() );
        personEntity.setBaptismDay( dto.getBaptismDay() );
        personEntity.setDeathYear( dto.getDeathYear() );
        personEntity.setDeathMonth( dto.getDeathMonth() );
        personEntity.setDeathDay( dto.getDeathDay() );
        personEntity.setBurialYear( dto.getBurialYear() );
        personEntity.setBurialMonth( dto.getBurialMonth() );
        personEntity.setBurialDay( dto.getBurialDay() );

        personEntity.setMother( mapParent(dto.getMotherId()) );
        personEntity.setFather( mapParent(dto.getFatherId()) );

        return personEntity;
    }

    @Override
    public void updatePersonEntity(PersonDTO dto, PersonEntity entity) {
        if ( dto == null ) {
            return;
        }

        if ( entity.getOccupations() != null ) {
            List<PersonOccupationEntity> list = personOccupationDTOListToPersonOccupationEntityList( dto.getOccupations() );
            if ( list != null ) {
                entity.getOccupations().clear();
                entity.getOccupations().addAll( list );
            }
        }
        else {
            List<PersonOccupationEntity> list = personOccupationDTOListToPersonOccupationEntityList( dto.getOccupations() );
            if ( list != null ) {
                entity.setOccupations( list );
            }
        }
        if ( entity.getSourceEvidences() != null ) {
            List<PersonSourceEvidenceEntity> list1 = personSourceEvidenceDTOListToPersonSourceEvidenceEntityList( dto.getSourceEvidences() );
            if ( list1 != null ) {
                entity.getSourceEvidences().clear();
                entity.getSourceEvidences().addAll( list1 );
            }
        }
        else {
            List<PersonSourceEvidenceEntity> list1 = personSourceEvidenceDTOListToPersonSourceEvidenceEntityList( dto.getSourceEvidences() );
            if ( list1 != null ) {
                entity.setSourceEvidences( list1 );
            }
        }
        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getGivenName() != null ) {
            entity.setGivenName( dto.getGivenName() );
        }
        if ( dto.getSurname() != null ) {
            entity.setSurname( dto.getSurname() );
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
        if ( dto.getExternalId() != null ) {
            entity.setExternalId( dto.getExternalId() );
        }
        if ( dto.getBioNote() != null ) {
            entity.setBioNote( dto.getBioNote() );
        }
        if ( dto.getBirthPlace() != null ) {
            if ( entity.getBirthPlace() == null ) {
                entity.setBirthPlace( new LocationEntity() );
            }
            locationMapper.updateLocationEntity( dto.getBirthPlace(), entity.getBirthPlace() );
        }
        if ( dto.getDeathPlace() != null ) {
            if ( entity.getDeathPlace() == null ) {
                entity.setDeathPlace( new LocationEntity() );
            }
            locationMapper.updateLocationEntity( dto.getDeathPlace(), entity.getDeathPlace() );
        }
        if ( dto.getBurialPlace() != null ) {
            if ( entity.getBurialPlace() == null ) {
                entity.setBurialPlace( new LocationEntity() );
            }
            locationMapper.updateLocationEntity( dto.getBurialPlace(), entity.getBurialPlace() );
        }
        if ( dto.getBaptismPlace() != null ) {
            if ( entity.getBaptismPlace() == null ) {
                entity.setBaptismPlace( new LocationEntity() );
            }
            locationMapper.updateLocationEntity( dto.getBaptismPlace(), entity.getBaptismPlace() );
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
        if ( dto.getBaptismYear() != null ) {
            entity.setBaptismYear( dto.getBaptismYear() );
        }
        if ( dto.getBaptismMonth() != null ) {
            entity.setBaptismMonth( dto.getBaptismMonth() );
        }
        if ( dto.getBaptismDay() != null ) {
            entity.setBaptismDay( dto.getBaptismDay() );
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

        entity.setMother( mapParent(dto.getMotherId()) );
        entity.setFather( mapParent(dto.getFatherId()) );
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

    protected List<PersonOccupationDTO> personOccupationEntityListToPersonOccupationDTOList(List<PersonOccupationEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<PersonOccupationDTO> list1 = new ArrayList<PersonOccupationDTO>( list.size() );
        for ( PersonOccupationEntity personOccupationEntity : list ) {
            list1.add( personOccupationMapper.toDTO( personOccupationEntity ) );
        }

        return list1;
    }

    protected List<PersonSourceEvidenceDTO> personSourceEvidenceEntityListToPersonSourceEvidenceDTOList(List<PersonSourceEvidenceEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<PersonSourceEvidenceDTO> list1 = new ArrayList<PersonSourceEvidenceDTO>( list.size() );
        for ( PersonSourceEvidenceEntity personSourceEvidenceEntity : list ) {
            list1.add( personSourceEvidenceMapper.toDTO( personSourceEvidenceEntity ) );
        }

        return list1;
    }

    protected List<PersonOccupationEntity> personOccupationDTOListToPersonOccupationEntityList(List<PersonOccupationDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<PersonOccupationEntity> list1 = new ArrayList<PersonOccupationEntity>( list.size() );
        for ( PersonOccupationDTO personOccupationDTO : list ) {
            list1.add( personOccupationMapper.toEntity( personOccupationDTO ) );
        }

        return list1;
    }

    protected List<PersonSourceEvidenceEntity> personSourceEvidenceDTOListToPersonSourceEvidenceEntityList(List<PersonSourceEvidenceDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<PersonSourceEvidenceEntity> list1 = new ArrayList<PersonSourceEvidenceEntity>( list.size() );
        for ( PersonSourceEvidenceDTO personSourceEvidenceDTO : list ) {
            list1.add( personSourceEvidenceMapper.toEntity( personSourceEvidenceDTO ) );
        }

        return list1;
    }
}
