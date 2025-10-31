package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.rodro.constant.CauseOfDeath;
import cz.rodro.constant.Gender;
import cz.rodro.constant.SocialStatus;
import cz.rodro.validation.Create;
import cz.rodro.validation.Update;
import cz.rodro.validation.ValidPartialDate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object for a Person.
 * Supports creation, update, and transfer of person details,
 * partial dates for life events, relationships, occupations,
 * source evidences, and optional military services.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ValidPartialDate(yearField = "birthYear", monthField = "birthMonth", dayField = "birthDay", message = "Birth date is invalid", allowNegativeYear = true)
@ValidPartialDate(yearField = "baptismYear", monthField = "baptismMonth", dayField = "baptismDay", message = "Baptism date is invalid", allowNegativeYear = true)
@ValidPartialDate(yearField = "deathYear", monthField = "deathMonth", dayField = "deathDay", message = "Death date is invalid", allowNegativeYear = true)
@ValidPartialDate(yearField = "burialYear", monthField = "burialMonth", dayField = "burialDay", message = "Burial date is invalid", allowNegativeYear = true)
public class PersonDTO {

    @JsonProperty("_id")
    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    private Long id;

    @NotBlank(groups = Create.class)
    @Size(max = 100)
    private String givenName;

    @NotBlank(groups = Create.class)
    @Size(max = 100)
    private String surname;

    @NotNull(groups = Create.class)
    @Size(max = 50)
    private String externalId;

    private Gender gender;

    @Size(max = 1000)
    private String bioNote;

    private LocationDTO birthPlace;
    private LocationDTO baptismPlace;
    private LocationDTO deathPlace;
    private LocationDTO burialPlace;

    @Positive
    private Long motherId;

    @Positive
    private Long fatherId;

    private SocialStatus socialStatus;
    private CauseOfDeath causeOfDeath;

    @Valid
    private List<PersonOccupationDTO> occupations;

    @Valid
    private List<PersonSourceEvidenceDTO> sourceEvidences;

    @Valid
    private List<PersonMilitaryServiceDTO> militaryServices; // Optional

    // --- Partial Dates ---
    @Min(-9999)
    private Integer birthYear;
    @Min(1) @Max(12)
    private Integer birthMonth;
    @Min(1) @Max(31)
    private Integer birthDay;

    @Min(-9999)
    private Integer baptismYear;
    @Min(1) @Max(12)
    private Integer baptismMonth;
    @Min(1) @Max(31)
    private Integer baptismDay;

    @Min(-9999)
    private Integer deathYear;
    @Min(1) @Max(12)
    private Integer deathMonth;
    @Min(1) @Max(31)
    private Integer deathDay;

    @Min(-9999)
    private Integer burialYear;
    @Min(1) @Max(12)
    private Integer burialMonth;
    @Min(1) @Max(31)
    private Integer burialDay;
}
