package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import cz.rodro.constant.CauseOfDeath;
import cz.rodro.constant.Gender;
import cz.rodro.constant.SocialStatus;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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

    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate baptizationDate;

    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deathDate;

    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate burialDate;

    private List<PersonSourceEvidenceDTO> sourceEvidences;

}
