package cz.rodro.dto.mapper;

import cz.rodro.dto.InstitutionDTO;
import cz.rodro.dto.OccupationDTO;
import cz.rodro.entity.InstitutionEntity;
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
        institutionEntity.setId( dto.getId() );
        institutionEntity.setInstitutionName( dto.getInstitutionName() );
        institutionEntity.setInstitutionDescription( dto.getInstitutionDescription() );
        institutionEntity.setOccupations( occupationDTOListToOccupationEntityList( dto.getOccupations() ) );

        return institutionEntity;
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
