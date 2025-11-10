package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object for a Military Organization (e.g., "Prussian Army", "French Guard").
 * Contains high-level details about the organization, its scope, and its components.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MilitaryOrganizationDTO {

    /**
     * The unique identifier of the military organization.
     */
    @JsonProperty("id")
    private Long id;

    /**
     * The official name of the organization (e.g., "Army of the Rhine").
     */
    private String name;

    /**
     * The primary branch of the organization (e.g., "Infantry").
     */
    private MilitaryArmyBranchDTO armyBranch;

    /**
     * The ID of the country this organization primarily belongs to.
     */
    private Long countryId;

    /**
     * The name of the country this organization primarily belongs to.
     */
    private String countryName;

    /**
     * The year the organization was officially formed or became active.
     */
    private String activeFromYear;

    /**
     * The year the organization was officially disbanded or became inactive. Null if currently active.
     */
    private String activeToYear;

    /**
     * A list of structures (e.g., Regiments, Brigades) that compose this organization.
     * Uses a simple DTO to avoid deep nesting.
     */
    private List<MilitaryStructureSimpleDTO> structures;

    /**
     * URL linking to an image (flag, crest, etc.) representing the organization.
     */
    private String imageUrl;

    /**
     * The full, detailed historical description of the organization.
     */
    private String description;

    /**
     * A short summary or contextual note regarding the organization's history.
     */
    private String historyContext;
}
