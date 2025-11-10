package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for a Military Army Branch (e.g., Infantry, Cavalry, Air Force).
 * This class is used to transfer concise information about a specific branch of military service.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MilitaryArmyBranchDTO {

    /**
     * The unique identifier of the military army branch.
     */
    @JsonProperty("id")
    private Long id;

    /**
     * The name of the army branch (e.g., "Infantry", "Artillery").
     * This field is used for display purposes and should be unique.
     */
    private String name;

    /**
     * A detailed description or definition of the army branch.
     */
    private String description;
}
