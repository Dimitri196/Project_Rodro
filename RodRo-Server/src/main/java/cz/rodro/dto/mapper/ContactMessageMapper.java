package cz.rodro.dto.mapper;

import cz.rodro.dto.ContactMessageDTO;
import cz.rodro.entity.ContactMessageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactMessageMapper {
    ContactMessageEntity toEntity(ContactMessageDTO dto);
    ContactMessageDTO toDTO(ContactMessageEntity entity);
}