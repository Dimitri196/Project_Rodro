package cz.rodro.dto.mapper;

import cz.rodro.dto.FamilyDTO;
import cz.rodro.entity.FamilyEntity;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class FamilyMapperImpl implements FamilyMapper {

    @Autowired
    private PersonMapper personMapper;
    @Autowired
    private LocationMapper locationMapper;

    @Override
    public FamilyDTO toFamilyDTO(FamilyEntity entity) {
        if ( entity == null ) {
            return null;
        }

        FamilyDTO familyDTO = new FamilyDTO();

        familyDTO.setId( entity.getId() );
        familyDTO.setMarriageDate( entity.getMarriageDate() );
        familyDTO.setMarriageLocation( locationMapper.toLocationDTO( entity.getMarriageLocation() ) );
        familyDTO.setSpouseMale( personMapper.toDTO( entity.getSpouseMale() ) );
        familyDTO.setMaritalStatusForSpouseMale( entity.getMaritalStatusForSpouseMale() );
        familyDTO.setSpouseFemale( personMapper.toDTO( entity.getSpouseFemale() ) );
        familyDTO.setMaritalStatusForSpouseFemale( entity.getMaritalStatusForSpouseFemale() );
        familyDTO.setWitnessesMaleSide1( personMapper.toDTO( entity.getWitnessesMaleSide1() ) );
        familyDTO.setWitnessesMaleSide2( personMapper.toDTO( entity.getWitnessesMaleSide2() ) );
        familyDTO.setWitnessesFemaleSide1( personMapper.toDTO( entity.getWitnessesFemaleSide1() ) );
        familyDTO.setWitnessesFemaleSide2( personMapper.toDTO( entity.getWitnessesFemaleSide2() ) );
        familyDTO.setSource( entity.getSource() );
        familyDTO.setNote( entity.getNote() );

        return familyDTO;
    }

    @Override
    public FamilyEntity toFamilyEntity(FamilyDTO dto) {
        if ( dto == null ) {
            return null;
        }

        FamilyEntity familyEntity = new FamilyEntity();

        familyEntity.setId( dto.getId() );
        familyEntity.setMarriageDate( dto.getMarriageDate() );
        familyEntity.setMaritalStatusForSpouseMale( dto.getMaritalStatusForSpouseMale() );
        familyEntity.setMaritalStatusForSpouseFemale( dto.getMaritalStatusForSpouseFemale() );
        familyEntity.setSource( dto.getSource() );
        familyEntity.setNote( dto.getNote() );

        return familyEntity;
    }

    @Override
    public void updateFamilyEntity(FamilyDTO dto, FamilyEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        entity.setMarriageDate( dto.getMarriageDate() );
        entity.setMaritalStatusForSpouseMale( dto.getMaritalStatusForSpouseMale() );
        entity.setMaritalStatusForSpouseFemale( dto.getMaritalStatusForSpouseFemale() );
        entity.setSource( dto.getSource() );
        entity.setNote( dto.getNote() );
    }
}
