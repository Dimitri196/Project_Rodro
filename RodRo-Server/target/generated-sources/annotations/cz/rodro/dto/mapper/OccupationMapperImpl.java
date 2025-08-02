package cz.rodro.dto.mapper;

import cz.rodro.dto.InstitutionDTO;
import cz.rodro.dto.LocationDTO;
import cz.rodro.dto.LocationHistoryDTO;
import cz.rodro.dto.OccupationDTO;
import cz.rodro.dto.PersonOccupationDTO;
import cz.rodro.dto.SourceDTO;
import cz.rodro.entity.InstitutionEntity;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.LocationHistoryEntity;
import cz.rodro.entity.OccupationEntity;
import cz.rodro.entity.PersonOccupationEntity;
import cz.rodro.entity.SourceEntity;
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

    @Override
    public void updateOccupationEntity(OccupationDTO dto, OccupationEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setOccupationName( dto.getOccupationName() );
        entity.setDescription( dto.getDescription() );
        if ( entity.getPersonOccupations() != null ) {
            List<PersonOccupationEntity> list = personOccupationDTOListToPersonOccupationEntityList( dto.getPersonOccupations() );
            if ( list != null ) {
                entity.getPersonOccupations().clear();
                entity.getPersonOccupations().addAll( list );
            }
            else {
                entity.setPersonOccupations( null );
            }
        }
        else {
            List<PersonOccupationEntity> list = personOccupationDTOListToPersonOccupationEntityList( dto.getPersonOccupations() );
            if ( list != null ) {
                entity.setPersonOccupations( list );
            }
        }
    }

    protected LocationHistoryDTO locationHistoryEntityToLocationHistoryDTO(LocationHistoryEntity locationHistoryEntity) {
        if ( locationHistoryEntity == null ) {
            return null;
        }

        LocationHistoryDTO locationHistoryDTO = new LocationHistoryDTO();

        locationHistoryDTO.setId( locationHistoryEntity.getId() );
        locationHistoryDTO.setCountryName( locationHistoryEntity.getCountryName() );
        locationHistoryDTO.setProvinceName( locationHistoryEntity.getProvinceName() );
        locationHistoryDTO.setDistrictName( locationHistoryEntity.getDistrictName() );
        locationHistoryDTO.setSubdivisionName( locationHistoryEntity.getSubdivisionName() );
        locationHistoryDTO.setStartDate( locationHistoryEntity.getStartDate() );
        locationHistoryDTO.setEndDate( locationHistoryEntity.getEndDate() );
        locationHistoryDTO.setNotes( locationHistoryEntity.getNotes() );

        return locationHistoryDTO;
    }

    protected List<LocationHistoryDTO> locationHistoryEntityListToLocationHistoryDTOList(List<LocationHistoryEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<LocationHistoryDTO> list1 = new ArrayList<LocationHistoryDTO>( list.size() );
        for ( LocationHistoryEntity locationHistoryEntity : list ) {
            list1.add( locationHistoryEntityToLocationHistoryDTO( locationHistoryEntity ) );
        }

        return list1;
    }

    protected SourceDTO sourceEntityToSourceDTO(SourceEntity sourceEntity) {
        if ( sourceEntity == null ) {
            return null;
        }

        SourceDTO sourceDTO = new SourceDTO();

        sourceDTO.setId( sourceEntity.getId() );
        sourceDTO.setSourceTitle( sourceEntity.getSourceTitle() );
        sourceDTO.setSourceReference( sourceEntity.getSourceReference() );
        sourceDTO.setSourceDescription( sourceEntity.getSourceDescription() );
        sourceDTO.setSourceUrl( sourceEntity.getSourceUrl() );
        sourceDTO.setSourceType( sourceEntity.getSourceType() );

        return sourceDTO;
    }

    protected List<SourceDTO> sourceEntityListToSourceDTOList(List<SourceEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<SourceDTO> list1 = new ArrayList<SourceDTO>( list.size() );
        for ( SourceEntity sourceEntity : list ) {
            list1.add( sourceEntityToSourceDTO( sourceEntity ) );
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
        locationDTO.setEstablishmentYear( locationEntity.getEstablishmentYear() );
        locationDTO.setGpsLatitude( locationEntity.getGpsLatitude() );
        locationDTO.setGpsLongitude( locationEntity.getGpsLongitude() );
        locationDTO.setSettlementType( locationEntity.getSettlementType() );
        locationDTO.setLocationHistories( locationHistoryEntityListToLocationHistoryDTOList( locationEntity.getLocationHistories() ) );
        locationDTO.setSources( sourceEntityListToSourceDTOList( locationEntity.getSources() ) );

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

    protected LocationHistoryEntity locationHistoryDTOToLocationHistoryEntity(LocationHistoryDTO locationHistoryDTO) {
        if ( locationHistoryDTO == null ) {
            return null;
        }

        LocationHistoryEntity locationHistoryEntity = new LocationHistoryEntity();

        locationHistoryEntity.setId( locationHistoryDTO.getId() );
        locationHistoryEntity.setCountryName( locationHistoryDTO.getCountryName() );
        locationHistoryEntity.setProvinceName( locationHistoryDTO.getProvinceName() );
        locationHistoryEntity.setDistrictName( locationHistoryDTO.getDistrictName() );
        locationHistoryEntity.setSubdivisionName( locationHistoryDTO.getSubdivisionName() );
        locationHistoryEntity.setStartDate( locationHistoryDTO.getStartDate() );
        locationHistoryEntity.setEndDate( locationHistoryDTO.getEndDate() );
        locationHistoryEntity.setNotes( locationHistoryDTO.getNotes() );

        return locationHistoryEntity;
    }

    protected List<LocationHistoryEntity> locationHistoryDTOListToLocationHistoryEntityList(List<LocationHistoryDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<LocationHistoryEntity> list1 = new ArrayList<LocationHistoryEntity>( list.size() );
        for ( LocationHistoryDTO locationHistoryDTO : list ) {
            list1.add( locationHistoryDTOToLocationHistoryEntity( locationHistoryDTO ) );
        }

        return list1;
    }

    protected SourceEntity sourceDTOToSourceEntity(SourceDTO sourceDTO) {
        if ( sourceDTO == null ) {
            return null;
        }

        SourceEntity sourceEntity = new SourceEntity();

        sourceEntity.setId( sourceDTO.getId() );
        sourceEntity.setSourceTitle( sourceDTO.getSourceTitle() );
        sourceEntity.setSourceDescription( sourceDTO.getSourceDescription() );
        sourceEntity.setSourceReference( sourceDTO.getSourceReference() );
        sourceEntity.setSourceType( sourceDTO.getSourceType() );
        sourceEntity.setSourceUrl( sourceDTO.getSourceUrl() );

        return sourceEntity;
    }

    protected List<SourceEntity> sourceDTOListToSourceEntityList(List<SourceDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<SourceEntity> list1 = new ArrayList<SourceEntity>( list.size() );
        for ( SourceDTO sourceDTO : list ) {
            list1.add( sourceDTOToSourceEntity( sourceDTO ) );
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
        locationEntity.setEstablishmentYear( locationDTO.getEstablishmentYear() );
        locationEntity.setGpsLatitude( locationDTO.getGpsLatitude() );
        locationEntity.setGpsLongitude( locationDTO.getGpsLongitude() );
        locationEntity.setSettlementType( locationDTO.getSettlementType() );
        locationEntity.setLocationHistories( locationHistoryDTOListToLocationHistoryEntityList( locationDTO.getLocationHistories() ) );
        locationEntity.setSources( sourceDTOListToSourceEntityList( locationDTO.getSources() ) );

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
}
