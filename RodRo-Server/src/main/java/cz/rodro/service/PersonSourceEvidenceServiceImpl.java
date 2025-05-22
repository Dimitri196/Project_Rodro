package cz.rodro.service;

import cz.rodro.dto.PersonSourceEvidenceDTO;
import cz.rodro.dto.mapper.PersonSourceEvidenceMapper;
import cz.rodro.entity.PersonSourceEvidenceEntity;
import cz.rodro.entity.repository.PersonSourceEvidenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonSourceEvidenceServiceImpl implements PersonSourceEvidenceService {

    private final PersonSourceEvidenceRepository repository;

    private final PersonSourceEvidenceMapper mapper;

    @Override
    public PersonSourceEvidenceDTO add(PersonSourceEvidenceDTO dto) {
        PersonSourceEvidenceEntity entity = mapper.toEntity(dto);
        PersonSourceEvidenceEntity saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    @Override
    public void remove(Long id) {
        repository.deleteById(id);
    }

    @Override
    public PersonSourceEvidenceDTO get(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElse(null);
    }

    @Override
    public List<PersonSourceEvidenceDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }
}
