package cz.rodro.service;

import cz.rodro.dto.ContactMessageDTO;
import cz.rodro.dto.mapper.ContactMessageMapper;
import cz.rodro.entity.ContactMessageEntity;
import cz.rodro.entity.repository.ContactMessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactMessageServiceImpl implements ContactMessageService {

    private final ContactMessageRepository repository;
    private final ContactMessageMapper mapper;

    public ContactMessageServiceImpl(ContactMessageRepository repository, ContactMessageMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ContactMessageDTO save(ContactMessageDTO dto) {
        ContactMessageEntity entity = mapper.toEntity(dto);
        ContactMessageEntity saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    @Override
    public List<ContactMessageDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }
}
