package cz.rodro.controller;

import cz.rodro.dto.OccupationDTO;
import cz.rodro.service.OccupationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OccupationController {

    @Autowired
    private OccupationService occupationService;

    /**
     * Creates a new occupation record.
     *
     * @param occupationDTO the occupation data transfer object containing information about the occupation to be created.
     * @return the created OccupationDTO object.
     */
    @PostMapping("/occupations")
    public OccupationDTO createOccupation(@Valid @RequestBody OccupationDTO occupationDTO) {
        return occupationService.createOccupation(occupationDTO);
    }

    /**
     * Retrieves a list of all occupations.
     *
     * @return a list of OccupationDTO objects representing all occupations.
     */
    @GetMapping("/occupations")
    public List<OccupationDTO> getAllOccupations() {
        return occupationService.getAllOccupations();
    }

    /**
     * Retrieves the details of a specific occupation based on its unique ID.
     *
     * @param occupationId the ID of the occupation to retrieve.
     * @return an OccupationDTO object containing the details of the requested occupation.
     */
    @GetMapping("/occupations/{occupationId}")
    public OccupationDTO getOccupationById(@PathVariable long occupationId) {
        return occupationService.getOccupationById(occupationId);
    }

    /**
     * Deletes an occupation record based on the provided occupation ID.
     *
     * @param occupationId the ID of the occupation to be deleted.
     */
    @DeleteMapping("/occupations/{occupationId}")
    public void deleteOccupation(@PathVariable long occupationId) {
        occupationService.deleteOccupation(occupationId);
    }

}
