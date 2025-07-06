package cz.rodro.dto.mapper;

import cz.rodro.dto.InstitutionDTO;
import cz.rodro.dto.LocationDTO;
import cz.rodro.dto.OccupationDTO;
import cz.rodro.dto.PersonOccupationDTO;
import cz.rodro.entity.InstitutionEntity;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.OccupationEntity;
import cz.rodro.entity.PersonOccupationEntity;
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
public class OccupationMapperImpl implements OccupationMapper {

    @Autowired
    private PersonOccupationMapper personOccupationMapper;

    @Override
    public OccupationDTO toDTO(OccupationEntity entity) {
        if ( entity == null ) {
            return null;
        }

        OccupationDTO occupationDTO = new OccupationDTO();

        occupationDTO.setId( entity.getId() );
        occupationDTO.setOccupationName( entity.getOccupationName() );
        occupationDTO.setDescription( entity.getDescription() );
        occupationDTO.setInstitution( institutionEntityToInstitutionDTO( entity.getInstitution() ) );
        occupationDTO.setPersonOccupations( personOccupationEntityListToPersonOccupationDTOList( entity.getPersonOccupations() ) );

        return occupationDTO;
    }

    @Override
    public OccupationEntity toEntity(OccupationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        OccupationEntity occupationEntity = new OccupationEntity();

        occupationEntity.setInstitution( institutionDTOToInstitutionEntity( dto.getInstitution() ) );
        occupationEntity.setId( dto.getId() );
        occupationEntity.setOccupationName( dto.getOccupationName() );
        occupationEntity.setDescription( dto.getDescription() );
        occupationEntity.setPersonOccupations( personOccupationDTOListToPersonOccupationEntityList( dto.getPersonOccupations() ) );

        return occupationEntity;
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

    protected InstitutionDTO institutionEntityToInstitutionDTO(InstitutionEntity institutionEntity) {
        if ( institutionEntity == null ) {
            return null;
        }

        InstitutionDTO institutionDTO = new InstitutionDTO();

        institutionDTO.setId( institutionEntity.getId() );
        institutionDTO.setInstitutionName( institutionEntity.getInstitutionName() );
        institutionDTO.setInstitutionDescription( institutionEntity.getInstitutionDescription() );
        institutionDTO.setInstitutionLocation( locationEntityToLocationDTO( institutionEntity.getInstitutionLocation() ) );

        return institutionDTO;
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

    protected List<OccupationEntity> occupationDTOListToOccupationEntityList(List<OccupationDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<OccupationEntity> list1 = new ArrayList<OccupationEntity>( list.size() );
        for ( OccupationDTO occupationDTO : list ) {
            list1.add( toEntity( occupationDTO ) );
        }

        return list1;
    }

    protected InstitutionEntity institutionDTOToInstitutionEntity(InstitutionDTO institutionDTO) {
        if ( institutionDTO == null ) {
            return null;
        }

        InstitutionEntity institutionEntity = new InstitutionEntity();

        institutionEntity.setId( institutionDTO.getId() );
        institutionEntity.setInstitutionName( institutionDTO.getInstitutionName() );
        institutionEntity.setInstitutionDescription( institutionDTO.getInstitutionDescription() );
        institutionEntity.setInstitutionLocation( locationDTOToLocationEntity( institutionDTO.getInstitutionLocation() ) );
        institutionEntity.setOccupations( occupationDTOListToOccupationEntityList( institutionDTO.getOccupations() ) );

        return institutionEntity;
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
}
