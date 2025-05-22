package cz.rodro.controller;

import cz.rodro.dto.FamilyDTO;
import cz.rodro.dto.PersonDTO;
import cz.rodro.dto.PersonOccupationDTO;
import cz.rodro.dto.PersonSourceEvidenceDTO;
import cz.rodro.dto.mapper.FamilyMapper;
import cz.rodro.dto.mapper.PersonMapper;
import cz.rodro.entity.PersonEntity;
import cz.rodro.entity.repository.PersonRepository;
import cz.rodro.entity.repository.PersonSourceEvidenceRepository;
import cz.rodro.service.PersonOccupationService;
import cz.rodro.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import cz.rodro.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing persons and their related sales and purchase invoices.
 * Provides endpoints for creating, retrieving, updating, and deleting person records,
 * as well as fetching the sales and purchases related to a person identified by their
 * identification number.
 */
@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    private PersonService personService;

    @Autowired
    private FamilyMapper familyMapper;

    @Autowired
    private PersonOccupationService personOccupationService;

    @Autowired
    private PersonSourceEvidenceRepository personSourceEvidenceRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonMapper personMapper;

    // Add a new person
    @PostMapping("/persons")
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDTO addPerson(@Valid @RequestBody PersonDTO personDTO) {
        return personService.addPerson(personDTO);
    }

    // Get all persons
    @GetMapping("/persons")
    public List<PersonDTO> getPersons() {
        return personService.getAll();
    }

    // Get person by ID
    @GetMapping("/persons/{personId}")
    public PersonDTO getPerson(@PathVariable Long personId) {
        return personService.getPerson(personId);
    }

    // Delete person by ID
    @DeleteMapping("/persons/{personId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable Long personId) {
        personService.removePerson(personId);
    }

    // Update existing person
    @PutMapping("/persons/{personId}")
    public PersonDTO updatePerson(
            @PathVariable Long personId,
            @Valid @RequestBody PersonDTO personDTO) {
        return personService.updatePerson(personId, personDTO);
    }

    // Get all families (spouses) of a person
    @GetMapping("/persons/{personId}/families")
    public List<FamilyDTO> getPersonFamilies(@PathVariable Long personId) {
        return personService.getSpouses(personId).stream()
                .map(family -> familyMapper.toFamilyDTO(family))
                .collect(Collectors.toList());
    }

    @PostMapping("/persons/{personId}/occupations")
    @ResponseStatus(HttpStatus.CREATED)
    public PersonOccupationDTO addOccupationToPerson(
            @PathVariable Long personId,
            @Valid @RequestBody PersonOccupationDTO dto) {
        dto.setPersonId(personId);
        return personOccupationService.createLink(dto);
    }

    // 🔹 Get all occupations of a person
    @GetMapping("/persons/{personId}/occupations")
    public List<PersonOccupationDTO> getPersonOccupations(@PathVariable Long personId) {
        return personOccupationService.getByPersonId(personId);
    }

    // 🔹 Remove person-occupation link
    @DeleteMapping("/persons/occupations/{linkId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePersonOccupation(@PathVariable Long linkId) {
        personOccupationService.deleteLink(linkId);
    }

    // Get all source evidences for a person
    @GetMapping("/persons/{personId}/sourceEvidences")
    public List<PersonSourceEvidenceDTO> getPersonSourceEvidences(@PathVariable Long personId) {
        return personService.getSourceEvidences(personId);
    }

    @DeleteMapping("/persons/sourceEvidences/{evidenceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePersonSourceEvidence(@PathVariable Long evidenceId) {
        personSourceEvidenceRepository.deleteById(evidenceId);
    }

    @GetMapping("/persons/{id}/children")
    public List<PersonDTO> getChildren(@PathVariable Long id) {
        PersonEntity parent = personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person not found"));
        List<PersonEntity> children = personRepository.findChildrenByParent(parent);
        return children.stream().map(personMapper::toDTO).collect(Collectors.toList());
    }

}
