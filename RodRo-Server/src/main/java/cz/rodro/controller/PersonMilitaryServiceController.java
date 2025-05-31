package cz.rodro.controller;

import cz.rodro.dto.PersonMilitaryServiceDTO;
import cz.rodro.service.PersonMilitaryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonMilitaryServiceController {

    @Autowired
    private PersonMilitaryService personMilitaryService;

    @GetMapping("/personMilitaryServices")
    public List<PersonMilitaryServiceDTO> getAllOrByPersonId(@RequestParam(required = false) Long personId) {
        if (personId != null) {
            return personMilitaryService.getByPersonId(personId);
        }
        return personMilitaryService.getAll();
    }

    @GetMapping("/personMilitaryServices/{id}")
    public PersonMilitaryServiceDTO getById(@PathVariable Long id) {
        return personMilitaryService.getPersonMilitaryService(id);
    }

    @PostMapping("/personMilitaryServices")
    @ResponseStatus(HttpStatus.CREATED)
    public PersonMilitaryServiceDTO add(@Valid @RequestBody PersonMilitaryServiceDTO dto) {
        return personMilitaryService.addPersonMilitaryService(dto);
    }

    @PutMapping("/personMilitaryServices/{id}")
    public PersonMilitaryServiceDTO update(@PathVariable Long id, @Valid @RequestBody PersonMilitaryServiceDTO dto) {
        return personMilitaryService.updatePersonMilitaryService(id, dto);
    }

    @DeleteMapping("/personMilitaryServices/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        personMilitaryService.removePersonMilitaryService(id);
    }
}