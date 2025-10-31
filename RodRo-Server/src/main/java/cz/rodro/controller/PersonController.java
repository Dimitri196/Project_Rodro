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
 * REST controller for managing persons and their related data such as occupations,
 * source evidences, families (spouses), and children.
 * <p>
 * Provides endpoints for creating, retrieving, updating, and deleting persons,
 * as well as retrieving related information.
 * <p>
 * Supports pagination, filtering, and sorting for list endpoints.
 */
@RestController
@RequestMapping("/api")
public class PersonController {

    private final PersonService personService;
    private final FamilyMapper familyMapper;
    private final PersonOccupationService personOccupationService;
    private final PersonSourceEvidenceRepository personSourceEvidenceRepository;
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Autowired
    public PersonController(
            PersonService personService,
            FamilyMapper familyMapper,
            PersonOccupationService personOccupationService,
            PersonSourceEvidenceRepository personSourceEvidenceRepository,
            PersonRepository personRepository,
            PersonMapper personMapper
    ) {
        this.personService = personService;
        this.familyMapper = familyMapper;
        this.personOccupationService = personOccupationService;
        this.personSourceEvidenceRepository = personSourceEvidenceRepository;
        this.personRepository = personRepository;
        this.personMapper = personMapper;
    }

    /**
     * Adds a new person.
     * Requires ROLE_ADMIN authority.
     *
     * @param personDTO Person data
     * @return ResponseEntity with status 201 Created and the created PersonDTO
     */
    @Secured("ROLE_ADMIN")
    @PostMapping("/persons")
    public ResponseEntity<PersonDTO> addPerson(@Valid @RequestBody PersonDTO personDTO) {
        PersonDTO createdPerson = personService.addPerson(personDTO);
        return ResponseEntity
                .created(URI.create("/api/persons/" + createdPerson.getId()))
                .body(createdPerson);
    }

    /**
     * Retrieves a paginated, optionally filtered and sorted list of persons.
     *
     * @param page       page number (0-indexed)
     * @param size       page size
     * @param sortBy     field to sort by
     * @param sortOrder  "asc" or "desc"
     * @param searchTerm optional search term
     * @return Page of PersonListProjection DTOs
     */
    @GetMapping("/persons")
    public ResponseEntity<Page<PersonListProjection>> getPersons(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "givenName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(required = false) String searchTerm
    ) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder.toUpperCase()), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PersonListProjection> personsPage = personService.getAllPersons(searchTerm, pageable);
        return ResponseEntity.ok(personsPage);
    }

    /**
     * Retrieves a person by ID.
     *
     * @param personId ID of the person
     * @return PersonDTO
     */
    @GetMapping("/persons/{personId}")
    public ResponseEntity<PersonDTO> getPerson(@PathVariable Long personId) {
        return ResponseEntity.ok(personService.getPerson(personId));
    }

    /**
     * Deletes a person by ID.
     * Requires ROLE_ADMIN authority.
     *
     * @param personId ID of the person
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/persons/{personId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerson(@PathVariable Long personId) {
        personService.removePerson(personId);
    }

    /**
     * Updates an existing person.
     * Requires ROLE_ADMIN authority.
     *
     * @param personId  ID in path
     * @param personDTO updated data
     * @return updated PersonDTO
     */
    @Secured("ROLE_ADMIN")
    @PutMapping("/persons/{personId}")
    public ResponseEntity<PersonDTO> updatePerson(
            @PathVariable Long personId,
            @Valid @RequestBody PersonDTO personDTO
    ) {
        if (!personId.equals(personDTO.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(personService.updatePerson(personId, personDTO));
    }

    /**
     * Retrieves all families (spouses) of a person.
     *
     * @param personId ID of the person
     * @return List of FamilyDTO
     */
    @GetMapping("/persons/{personId}/families")
    public ResponseEntity<List<FamilyDTO>> getPersonFamilies(@PathVariable Long personId) {
        List<FamilyDTO> families = personService.getSpouses(personId).stream()
                .map(familyMapper::toFamilyDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(families);
    }

    /**
     * Adds an occupation link to a person.
     * Requires ROLE_ADMIN authority.
     *
     * @param personId person ID
     * @param dto      occupation link
     * @return created PersonOccupationDTO
     */
    @Secured("ROLE_ADMIN")
    @PostMapping("/persons/{personId}/occupations")
    public ResponseEntity<PersonOccupationDTO> addOccupationToPerson(
            @PathVariable Long personId,
            @Valid @RequestBody PersonOccupationDTO dto
    ) {
        dto.setPersonId(personId);
        PersonOccupationDTO createdLink = personOccupationService.createLink(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLink);
    }

    /**
     * Retrieves all occupations of a person.
     *
     * @param personId person ID
     * @return List of PersonOccupationDTO
     */
    @GetMapping("/persons/{personId}/occupations")
    public ResponseEntity<List<PersonOccupationDTO>> getPersonOccupations(@PathVariable Long personId) {
        return ResponseEntity.ok(personOccupationService.getByPersonId(personId));
    }

    /**
     * Deletes a person-occupation link.
     * Requires ROLE_ADMIN authority.
     *
     * @param linkId ID of the link
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/persons/occupations/{linkId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePersonOccupation(@PathVariable Long linkId) {
        personOccupationService.deleteLink(linkId);
    }

    /**
     * Retrieves all source evidences linked to a person.
     *
     * @param personId person ID
     * @return list of PersonSourceEvidenceDTO
     */
    @GetMapping("/persons/{personId}/sourceEvidences")
    public ResponseEntity<List<PersonSourceEvidenceDTO>> getPersonSourceEvidences(@PathVariable Long personId) {
        return ResponseEntity.ok(personService.getSourceEvidences(personId));
    }

    /**
     * Deletes a source evidence link from a person.
     * Requires ROLE_ADMIN authority.
     *
     * @param evidenceId ID of the evidence link
     */
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/persons/sourceEvidences/{evidenceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePersonSourceEvidence(@PathVariable Long evidenceId) {
        personSourceEvidenceRepository.deleteById(evidenceId);
    }

    /**
     * Retrieves all children of a person.
     *
     * @param id parent ID
     * @return List of PersonDTO representing children
     */
    @GetMapping("/persons/{id}/children")
    public ResponseEntity<List<PersonDTO>> getChildren(@PathVariable Long id) {
        PersonEntity parent = personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person not found"));
        List<PersonDTO> childrenDTOs = personRepository.findChildrenByParent(parent).stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(childrenDTOs);
    }

}