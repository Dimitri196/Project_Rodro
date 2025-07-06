package cz.rodro.service;

import cz.rodro.dto.ContactMessageDTO;

import java.util.List;

public interface ContactMessageService {
    ContactMessageDTO save(ContactMessageDTO dto);

    List<ContactMessageDTO> getAll();
}
