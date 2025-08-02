package cz.rodro.dto.mapper;

import cz.rodro.dto.ContactMessageDTO;
import cz.rodro.entity.ContactMessageEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class ContactMessageMapperImpl implements ContactMessageMapper {

    @Override
    public ContactMessageEntity toEntity(ContactMessageDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ContactMessageEntity contactMessageEntity = new ContactMessageEntity();

        contactMessageEntity.setId( dto.getId() );
        contactMessageEntity.setName( dto.getName() );
        contactMessageEntity.setEmail( dto.getEmail() );
        contactMessageEntity.setMessage( dto.getMessage() );

        return contactMessageEntity;
    }

    @Override
    public ContactMessageDTO toDTO(ContactMessageEntity entity) {
        if ( entity == null ) {
            return null;
        }

        ContactMessageDTO contactMessageDTO = new ContactMessageDTO();

        contactMessageDTO.setId( entity.getId() );
        contactMessageDTO.setName( entity.getName() );
        contactMessageDTO.setEmail( entity.getEmail() );
        contactMessageDTO.setMessage( entity.getMessage() );

        return contactMessageDTO;
    }
}
