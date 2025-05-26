package cz.rodro.service;

import cz.rodro.dto.PersonMilitaryServiceDTO;
import cz.rodro.dto.mapper.PersonMilitaryServiceMapper;
import cz.rodro.entity.PersonMilitaryServiceEntity;
import cz.rodro.entity.repository.PersonMilitaryServiceRepository;
import cz.rodro.exception.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonMilitaryServiceImpl implements PersonMilitaryService {

    @Autowired
    PersonMilitaryServiceRepository personMilitaryServiceRepository;

    @Autowired
    PersonMilitaryServiceMapper personMilitaryServiceMapper;

    @Override
    public List<PersonMilitaryServiceDTO> getAll() {
        return personMilitaryServiceRepository
                .findAll()
                .stream()
                .map(personMilitaryServiceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PersonMilitaryServiceDTO getPersonMilitaryService(long serviceId) {
        PersonMilitaryServiceEntity entity = personMilitaryServiceRepository.getReferenceById(serviceId);
        return personMilitaryServiceMapper.toDto(entity);
    }

    @Override
    public PersonMilitaryServiceDTO addPersonMilitaryService(PersonMilitaryServiceDTO dto) {
        PersonMilitaryServiceEntity entity = personMilitaryServiceMapper.toEntity(dto);
        entity = personMilitaryServiceRepository.save(entity);
        return personMilitaryServiceMapper.toDto(entity);
    }

    @Override
    public void removePersonMilitaryService(long serviceId) {
        PersonMilitaryServiceEntity entity = personMilitaryServiceRepository.findById(serviceId)
                .orElseThrow(EntityNotFoundException::new);
        PersonMilitaryServiceDTO dto = personMilitaryServiceMapper.toDto(entity);
        personMilitaryServiceRepository.delete(entity);
    }

    @Override
    @Transactional
    public PersonMilitaryServiceDTO updatePersonMilitaryService(Long serviceId, PersonMilitaryServiceDTO dto) {
        PersonMilitaryServiceEntity entity = personMilitaryServiceRepository.findById(serviceId)
                .orElseThrow(() -> new NotFoundException("PersonMilitaryService with id " + serviceId + " wasn't found in the database"));
        personMilitaryServiceMapper.updatePersonMilitaryServiceEntity(dto, entity);
        personMilitaryServiceRepository.save(entity);
        return personMilitaryServiceMapper.toDto(entity);
    }

    @Override
    public PersonMilitaryServiceEntity fetchPersonMilitaryServiceById(Long id, String type) {
        return personMilitaryServiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(type + " with id " + id + " wasn't found in the database."));
    }

    public List<PersonMilitaryServiceDTO> getByPersonId(Long personId) {
        return personMilitaryServiceRepository.findByPersonId(personId)
                .stream()
                .map(personMilitaryServiceMapper::toDto)
                .collect(Collectors.toList());
    }

}