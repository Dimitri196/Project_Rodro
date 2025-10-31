package cz.rodro.dto.mapper;

import cz.rodro.dto.InstitutionDTO;
import cz.rodro.dto.OccupationDTO;
import cz.rodro.entity.InstitutionEntity;
import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.OccupationEntity;
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
public class InstitutionMapperImpl implements InstitutionMapper {

    @Autowired
    private LocationMapper locationMapper;
    @Autowired
    private OccupationMapper occupationMapper;

    @Override
    public InstitutionDTO toDto(InstitutionEntity entity) {
        if ( entity == null ) {
            return null;
        }

        InstitutionDTO institutionDTO = new InstitutionDTO();

        institutionDTO.setInstitutionLocation( locationMapper.toLocationDTO( entity.getInstitutionLocation() ) );
        institutionDTO.setSealImageUrl( entity.getSealImageUrl() );
        institutionDTO.setInstitutionType( entity.getInstitutionType() );
        institutionDTO.setId( entity.getId() );
        institutionDTO.setInstitutionName( entity.getInstitutionName() );
        institutionDTO.setInstitutionDescription( entity.getInstitutionDescription() );
        institutionDTO.setOccupations( occupationEntityListToOccupationDTOList( entity.getOccupations() ) );

        return institutionDTO;
    }

    @Override
    public InstitutionEntity toEntity(InstitutionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        InstitutionEntity institutionEntity = new InstitutionEntity();

        institutionEntity.setInstitutionLocation( locationMapper.toLocationEntity( dto.getInstitutionLocation() ) );
        institutionEntity.setSealImageUrl( dto.getSealImageUrl() );
        institutionEntity.setInstitutionType( dto.getInstitutionType() );
        institutionEntity.setId( dto.getId() );
        institutionEntity.setInstitutionName( dto.getInstitutionName() );
        institutionEntity.setInstitutionDescription( dto.getInstitutionDescription() );
        institutionEntity.setOccupations( occupationDTOListToOccupationEntityList( dto.getOccupations() ) );

        return institutionEntity;
    }

    @Override
    public void updateInstitutionEntity(InstitutionDTO dto, InstitutionEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setSealImageUrl( dto.getSealImageUrl() );
        entity.setInstitutionType( dto.getInstitutionType() );
        entity.setInstitutionName( dto.getInstitutionName() );
        entity.setInstitutionDescription( dto.getInstitutionDescription() );
        if ( dto.getInstitutionLocation() != null ) {
            if ( entity.getInstitutionLocation() == null ) {
                entity.setInstitutionLocation( new LocationEntity() );
            }
            locationMapper.updateLocationEntity( dto.getInstitutionLocation(), entity.getInstitutionLocation() );
        }
        else {
            entity.setInstitutionLocation( null );
        }
        if ( entity.getOccupations() != null ) {
            List<OccupationEntity> list = occupationDTOListToOccupationEntityList( dto.getOccupations() );
            if ( list != null ) {
                entity.getOccupations().clear();
                entity.getOccupations().addAll( list );
            }
            else {
                entity.setOccupations( null );
            }
        }
        else {
            List<OccupationEntity> list = occupationDTOListToOccupationEntityList( dto.getOccupations() );
            if ( list != null ) {
                entity.setOccupations( list );
            }
        }
    }

    protected List<OccupationDTO> occupationEntityListToOccupationDTOList(List<OccupationEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<OccupationDTO> list1 = new ArrayList<OccupationDTO>( list.size() );
        for ( OccupationEntity occupationEntity : list ) {
            list1.add( occupationMapper.toDTO( occupationEntity ) );
        }

        return list1;
    }

    protected List<OccupationEntity> occupationDTOListToOccupationEntityList(List<OccupationDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<OccupationEntity> list1 = new ArrayList<OccupationEntity>( list.size() );
        for ( OccupationDTO occupationDTO : list ) {
            list1.add( occupationMapper.toEntity( occupationDTO ) );
        }

        return list1;
    }
}
