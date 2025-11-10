package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.rodro.constant.InstitutionType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object (DTO) for the Institution entity.
 * This structure is optimized for API responses, providing the minimal country
 * data required for client-side display and linking without transferring the
 * full Country object.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionDTO {

    /**
     * Unique identifier for the institution. Mapped to '_id' for frontend frameworks.
     */
    @JsonProperty("_id")
    @NotNull
    private Long id;

    /**
     * The official name of the institution.
     */
    private String name;

    /**
     * A detailed description of the institution's purpose or history.
     */
    private String description;

    /**
     * The ID of the related Country Entity (used for linking or making further API calls).
     */
    private Long countryId;

    /**
     * The display name of the related Country (used for immediate UI presentation).
     */
    private String countryName;

    /**
     * The year or era the institution was established.
     * Stored as a String to support BC/BCE dates and approximate values (e.g., "300 BC").
     */
    private String establishmentYear;

    /**
     * The year or era the institution was dissolved or ceased to exist. Null if currently active.
     * Stored as a String.
     */
    private String cancellationYear;

    /**
     * The ID of the specific geographical location.
     */
    private Long locationId;

    /**
     * The display name of the specific geographical location.
     */
    private String locationName;

    /**
     * A list of occupations or positions associated with this institution.
     */
    private List<OccupationDTO> occupations;

    /**
     * URL for the image of the institution's official seal or emblem.
     */
    private String sealImageUrl;

    /**
     * The categorical type of the institution (e.g., ROYAL, MILITARY, ACADEMIC).
     */
    private InstitutionType type;

}