package cz.rodro.controller;

import cz.rodro.dto.FamilyDTO;
import cz.rodro.entity.filter.FamilyFilter;
import cz.rodro.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FamilyController {

    @Autowired
    private FamilyService familyService;

    /**
     * Creates a new family.
     *
     * @param familyDTO the data transfer object containing details of the family to be created.
     * @return the created FamilyDTO object.
     */
    @PostMapping("/families")
    public FamilyDTO addFamily(@RequestBody FamilyDTO familyDTO) {
        return familyService.addFamily(familyDTO);
    }

    /**
     * Retrieves a specific family by its unique ID.
     *
     * @param familyId the ID of the family to retrieve.
     * @return a FamilyDTO object containing the details of the requested family.
     */
    @GetMapping("/families/{familyId}")
    public FamilyDTO getFamily(@PathVariable Long familyId) {
        return familyService.getFamily(familyId);
    }

    /**
     * Updates the details of an existing family.
     *
     * @param familyId  the ID of the family to update.
     * @param familyDTO the new FamilyDTO object containing updated family details.
     * @return the updated FamilyDTO object.
     */
    @PutMapping("/families/{familyId}")
    public FamilyDTO updateFamily(
            @PathVariable Long familyId,
            @RequestBody FamilyDTO familyDTO) {
        return familyService.updateFamily(familyId, familyDTO);
    }

    /**
     * Deletes a family by its unique ID.
     * Responds with a 204 No Content status if the deletion is successful.
     *
     * @param familyId the ID of the family to delete.
     */
    @DeleteMapping("/families/{familyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFamily(@PathVariable Long familyId) {
        familyService.removeFamily(familyId);
    }



    /**
     * Retrieves a list of all families.
     * This can be extended to include filtering if filter criteria are added later.
     *
     * @return a list of FamilyDTO objects.
     */

    @GetMapping("/families")
    public List<FamilyDTO> getAllFamilies(FamilyFilter familyFilter) {
        return familyService.getAllFamilies(familyFilter);
    }

}
