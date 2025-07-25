package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.rodro.constant.CauseOfDeath;
import cz.rodro.constant.Gender;
import cz.rodro.constant.SocialStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data transfer object (DTO) for a person.
 * This class represents a person with various fields such as id, name, identification number,
 * tax number, bank information, contact details, and other personal information.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    @JsonProperty("_id")
    private Long id;

    private String givenName;
    private String givenSurname;
    private Gender gender;
    private String identificationNumber;
    private String note;
    private LocationDTO birthPlace;
    private LocationDTO baptizationPlace;
    private LocationDTO deathPlace;
    private LocationDTO burialPlace;
    private PersonDTO mother;
    private PersonDTO father;

    private SocialStatus socialStatus;
    private CauseOfDeath causeOfDeath;
    private List<PersonOccupationDTO> occupations;
    private String institutionLocationName;

    // Birth Date
    private Integer birthYear;
    private Integer birthMonth; // 1-12, or null if unknown
    private Integer birthDay;   // 1-31, or null if unknown

    // Baptization Date
    private Integer baptizationYear;
    private Integer baptizationMonth;
    private Integer baptizationDay;

    // Death Date
    private Integer deathYear;
    private Integer deathMonth;
    private Integer deathDay;

    // Burial Date
    private Integer burialYear;
    private Integer burialMonth;
    private Integer burialDay;

    private List<PersonSourceEvidenceDTO> sourceEvidences;

}
