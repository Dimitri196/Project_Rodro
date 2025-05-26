package cz.rodro.dto.mapper;

import cz.rodro.dto.PersonMilitaryServiceDTO;
import cz.rodro.entity.MilitaryRankEntity;
import cz.rodro.entity.MilitaryStructureEntity;
import cz.rodro.entity.PersonEntity;
import cz.rodro.entity.PersonMilitaryServiceEntity;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class PersonMilitaryServiceMapperImpl implements PersonMilitaryServiceMapper {

    @Autowired
    private MilitaryRankMapper militaryRankMapper;
    @Autowired
    private MilitaryStructureMapper militaryStructureMapper;

    @Override
    public PersonMilitaryServiceDTO toDto(PersonMilitaryServiceEntity entity) {
        if ( entity == null ) {
            return null;
        }

        PersonMilitaryServiceDTO personMilitaryServiceDTO = new PersonMilitaryServiceDTO();

        personMilitaryServiceDTO.setPersonId( entityPersonId( entity ) );
        personMilitaryServiceDTO.setId( entity.getId() );
        personMilitaryServiceDTO.setMilitaryStructure( militaryStructureMapper.toMilitaryStructureDTO( entity.getMilitaryStructure() ) );
        personMilitaryServiceDTO.setMilitaryRank( militaryRankMapper.toMilitaryRankDTO( entity.getMilitaryRank() ) );
        personMilitaryServiceDTO.setEnlistmentYear( entity.getEnlistmentYear() );
        personMilitaryServiceDTO.setDischargeYear( entity.getDischargeYear() );
        personMilitaryServiceDTO.setNotes( entity.getNotes() );

        return personMilitaryServiceDTO;
    }

    @Override
    public PersonMilitaryServiceEntity toEntity(PersonMilitaryServiceDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PersonMilitaryServiceEntity personMilitaryServiceEntity = new PersonMilitaryServiceEntity();

        personMilitaryServiceEntity.setId( dto.getId() );
        personMilitaryServiceEntity.setMilitaryStructure( militaryStructureMapper.toMilitaryStructureEntity( dto.getMilitaryStructure() ) );
        personMilitaryServiceEntity.setMilitaryRank( militaryRankMapper.toMilitaryRankEntity( dto.getMilitaryRank() ) );
        personMilitaryServiceEntity.setEnlistmentYear( dto.getEnlistmentYear() );
        personMilitaryServiceEntity.setDischargeYear( dto.getDischargeYear() );
        personMilitaryServiceEntity.setNotes( dto.getNotes() );

        return personMilitaryServiceEntity;
    }

    @Override
    public void updatePersonMilitaryServiceEntity(PersonMilitaryServiceDTO dto, PersonMilitaryServiceEntity entity) {
        if ( dto == null ) {
            return;
        }

        entity.setId( dto.getId() );
        if ( dto.getMilitaryStructure() != null ) {
            if ( entity.getMilitaryStructure() == null ) {
                entity.setMilitaryStructure( new MilitaryStructureEntity() );
            }
            militaryStructureMapper.updateMilitaryStructureEntity( dto.getMilitaryStructure(), entity.getMilitaryStructure() );
        }
        else {
            entity.setMilitaryStructure( null );
        }
        if ( dto.getMilitaryRank() != null ) {
            if ( entity.getMilitaryRank() == null ) {
                entity.setMilitaryRank( new MilitaryRankEntity() );
            }
            militaryRankMapper.updateMilitaryRankEntity( dto.getMilitaryRank(), entity.getMilitaryRank() );
        }
        else {
            entity.setMilitaryRank( null );
        }
        entity.setEnlistmentYear( dto.getEnlistmentYear() );
        entity.setDischargeYear( dto.getDischargeYear() );
        entity.setNotes( dto.getNotes() );
    }

    private Long entityPersonId(PersonMilitaryServiceEntity personMilitaryServiceEntity) {
        if ( personMilitaryServiceEntity == null ) {
            return null;
        }
        PersonEntity person = personMilitaryServiceEntity.getPerson();
        if ( person == null ) {
            return null;
        }
        Long id = person.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
