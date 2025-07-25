package cz.rodro.controller;

import cz.rodro.dto.*;
import cz.rodro.dto.mapper.FamilyMapper;
import cz.rodro.dto.mapper.PersonMapper;
import cz.rodro.entity.PersonEntity;
import cz.rodro.entity.repository.PersonRepository;
import cz.rodro.entity.repository.PersonSourceEvidenceRepository;
import cz.rodro.service.PersonOccupationService;
import cz.rodro.service.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import cz.rodro.exception.NotFoundException;

import java.net.URI;
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

    private final PersonService personService; // Use final and constructor injection
    private final FamilyMapper familyMapper; // Use final and constructor injection
    private final PersonOccupationService personOccupationService; // Use final and constructor injection
    private final PersonSourceEvidenceRepository personSourceEvidenceRepository; // Use final and constructor injection
    private final PersonRepository personRepository; // Use final and constructor injection
    private final PersonMapper personMapper; // Use final and constructor injection

    // Constructor injection for all dependencies
    @Autowired
    public PersonController(
            PersonService personService,
            FamilyMapper familyMapper,
            PersonOccupationService personOccupationService,
            PersonSourceEvidenceRepository personSourceEvidenceRepository,
            PersonRepository personRepository,
            PersonMapper personMapper) {
        this.personService = personService;
        this.familyMapper = familyMapper;
        this.personOccupationService = personOccupationService;
        this.personSourceEvidenceRepository = personSourceEvidenceRepository;
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    // Add a new person
    @Secured("ROLE_ADMIN")
    @PostMapping("/persons")
    public ResponseEntity<PersonDTO> addPerson(@Valid @RequestBody PersonDTO personDTO) {
        PersonDTO createdPerson = personService.addPerson(personDTO);
        // Return 201 Created status with location header
        return ResponseEntity.created(URI.create("/api/persons/" + createdPerson.getId())).body(createdPerson);
    }

    /**
     * Retrieves a paginated, filtered, and sorted list of persons.
     * This endpoint now returns a Page of PersonListProjection for optimized performance.
     *
     * @param page The page number (0-indexed, default 0).
     * @param size The number of items per page (default 10).
     * @param sortBy The field to sort by (default "givenName").
     * @param sortOrder The sort order ("asc" or "desc", default "asc").
     * @param searchTerm Optional search term for filtering by name or ID.
     * @return A Page object containing PersonListProjection DTOs.
     */
    @GetMapping("/persons")
    public ResponseEntity<Page<PersonListProjection>> getPersons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "givenName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(required = false) String searchTerm) {

        // Create a Sort object from sortBy and sortOrder
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder.toUpperCase()), sortBy);
        // Create a Pageable object
        Pageable pageable = PageRequest.of(page, size, sort);

        // Call the service method with search term and pageable
        Page<PersonListProjection> personsPage = personService.getAllPersons(searchTerm, pageable);
        return ResponseEntity.ok(personsPage);
    }

    // Get person by ID
    @GetMapping("/persons/{personId}")
    public ResponseEntity<PersonDTO> getPerson(@PathVariable Long personId) {
        PersonDTO personDTO = personService.getPerson(personId);
        return ResponseEntity.ok(personDTO);
    }

    // Delete person by ID
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/persons/{personId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // This is already good, no need for ResponseEntity.noContent()
    public void deletePerson(@PathVariable Long personId) {
        personService.removePerson(personId);
    }

    // Update existing person
    @Secured("ROLE_ADMIN")
    @PutMapping("/persons/{personId}")
    public ResponseEntity<PersonDTO> updatePerson(
            @PathVariable Long personId,
            @Valid @RequestBody PersonDTO personDTO) {
        // Ensure the ID in the path matches the ID in the DTO
        if (!personId.equals(personDTO.getId())) {
            return ResponseEntity.badRequest().build(); // Or throw a specific exception
        }
        PersonDTO updatedPerson = personService.updatePerson(personId, personDTO);
        return ResponseEntity.ok(updatedPerson);
    }

    // Get all families (spouses) of a person
    @GetMapping("/persons/{personId}/families")
    public ResponseEntity<List<FamilyDTO>> getPersonFamilies(@PathVariable Long personId) {
        List<FamilyDTO> families = personService.getSpouses(personId).stream()
                .map(familyMapper::toFamilyDTO) // Use method reference for cleaner code
                .collect(Collectors.toList());
        return ResponseEntity.ok(families);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/persons/{personId}/occupations")
    public ResponseEntity<PersonOccupationDTO> addOccupationToPerson(
            @PathVariable Long personId,
            @Valid @RequestBody PersonOccupationDTO dto) {
        dto.setPersonId(personId);
        PersonOccupationDTO createdLink = personOccupationService.createLink(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLink);
    }

    // 🔹 Get all occupations of a person
    @GetMapping("/persons/{personId}/occupations")
    public ResponseEntity<List<PersonOccupationDTO>> getPersonOccupations(@PathVariable Long personId) {
        List<PersonOccupationDTO> occupations = personOccupationService.getByPersonId(personId);
        return ResponseEntity.ok(occupations);
    }

    // 🔹 Remove person-occupation link
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/persons/occupations/{linkId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePersonOccupation(@PathVariable Long linkId) {
        personOccupationService.deleteLink(linkId);
    }

    // Get all source evidences for a person
    @GetMapping("/persons/{personId}/sourceEvidences")
    public ResponseEntity<List<PersonSourceEvidenceDTO>> getPersonSourceEvidences(@PathVariable Long personId) {
        List<PersonSourceEvidenceDTO> evidences = personService.getSourceEvidences(personId);
        return ResponseEntity.ok(evidences);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/persons/sourceEvidences/{evidenceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePersonSourceEvidence(@PathVariable Long evidenceId) {
        personSourceEvidenceRepository.deleteById(evidenceId);
    }

    @GetMapping("/persons/{id}/children")
    public ResponseEntity<List<PersonDTO>> getChildren(@PathVariable Long id) {
        PersonEntity parent = personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person not found"));
        List<PersonEntity> children = personRepository.findChildrenByParent(parent);
        List<PersonDTO> childrenDTOs = children.stream().map(personMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(childrenDTOs);
    }

}
