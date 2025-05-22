package cz.rodro.service;

import cz.rodro.dto.PersonOccupationDTO;
import cz.rodro.dto.mapper.PersonOccupationMapper;
import cz.rodro.entity.OccupationEntity;
import cz.rodro.entity.PersonEntity;
import cz.rodro.entity.PersonOccupationEntity;
import cz.rodro.entity.repository.OccupationRepository;
import cz.rodro.entity.repository.PersonOccupationRepository;
import cz.rodro.entity.repository.PersonRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import cz.rodro.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonOccupationServiceImpl implements PersonOccupationService {

    private final PersonOccupationRepository personOccupationRepository;
    private final PersonRepository personRepository;
    private final OccupationRepository occupationRepository;
    private final PersonOccupationMapper personOccupationMapper;

    @Override
    @Transactional
    public PersonOccupationDTO createLink(PersonOccupationDTO dto) {
        PersonEntity person = personRepository.findById(dto.getPersonId())
                .orElseThrow(() -> new NotFoundException("Person not found with ID: " + dto.getPersonId()));
        OccupationEntity occupation = occupationRepository.findById(dto.getOccupationId())
                .orElseThrow(() -> new NotFoundException("Occupation not found with ID: " + dto.getOccupationId()));

        PersonOccupationEntity entity = new PersonOccupationEntity();
        entity.setPerson(person);
        entity.setOccupation(occupation);
        entity.setOccupationStartDate(dto.getStartDate());
        entity.setOccupationEndDate(dto.getEndDate());

        PersonOccupationEntity saved = personOccupationRepository.save(entity);
        PersonOccupationDTO result = personOccupationMapper.toDTO(saved);
        result.setGivenName(person.getGivenName());
        result.setGivenSurname(person.getGivenSurname());
        return result;
    }

    @Override
    public List<PersonOccupationDTO> getAllLinks() {
        return personOccupationRepository.findAll().stream()
                .map(entity -> {
                    PersonOccupationDTO dto = personOccupationMapper.toDTO(entity);
                    PersonEntity person = entity.getPerson();
                    if (person != null) {
                        dto.setGivenName(person.getGivenName());
                        dto.setGivenSurname(person.getGivenSurname());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<PersonOccupationDTO> getByPersonId(Long personId) {
        return personOccupationRepository.findByPersonId(personId).stream()
                .map(entity -> {
                    PersonOccupationDTO dto = personOccupationMapper.toDTO(entity);
                    PersonEntity person = entity.getPerson();
                    if (person != null) {
                        dto.setGivenName(person.getGivenName());
                        dto.setGivenSurname(person.getGivenSurname());
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void deleteLink(Long id) {
        PersonOccupationEntity entity = personOccupationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Link not found with ID: " + id));
        personOccupationRepository.delete(entity);
    }
}