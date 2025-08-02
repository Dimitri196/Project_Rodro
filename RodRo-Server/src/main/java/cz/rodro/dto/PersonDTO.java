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
 * Data Transfer Object (DTO) for representing a Person.
 * This DTO is designed for efficient data transfer between the backend (Spring Boot)
 * and the frontend (React), providing a flattened and tailored view of the Person entity.
 *
 * It incorporates nullable fields for dates to accommodate incomplete historical data
 * and uses IDs for referenced entities to minimize payload size.
 */
@Data // Lombok annotation: Generates getters, setters, toString, equals, and hashCode methods.
@AllArgsConstructor // Lombok annotation: Generates a constructor with all fields.
@NoArgsConstructor // Lombok annotation: Generates a no-argument constructor.
public class PersonDTO {

    /**
     * Unique identifier for the person.
     * Mapped to "_id" for compatibility, potentially with NoSQL databases or specific frontend requirements.
     */
    @JsonProperty("_id")
    private Long id;

    /**
     * The given name(s) of the person.
     */
    private String givenName;

    /**
     * The surname(s) of the person.
     */
    private String givenSurname;

    /**
     * The gender of the person. Assumed to be an enumeration (e.g., Gender.MALE, Gender.FEMALE, Gender.OTHER).
     */
    private Gender gender; // Assuming Gender is an enum in your domain

    /**
     * An identification number for the person, if applicable (e.g., national ID, passport number).
     */
    private String identificationNumber;

    /**
     * General notes or remarks about the person.
     */
    private String note;

    /**
     * The ID of the geographical location where the person was born.
     * Transferred as an ID to reduce payload; full Location details are fetched separately if needed.
     */
    // Instead of Long birthPlaceId, etc., use LocationDTO objects
    private LocationDTO birthPlace;
    private LocationDTO baptizationPlace;
    private LocationDTO deathPlace;
    private LocationDTO burialPlace;

    /**
     * The ID of the person's mother.
     * Represents a relationship to another Person entity by ID.
     */
    private Long motherId;

    /**
     * The ID of the person's father.
     * Represents a relationship to another Person entity by ID.
     */
    private Long fatherId;

    /**
     * The social status of the person. Assumed to be an enumeration (e.g., SocialStatus.NOBLE, SocialStatus.COMMONER).
     */
    private SocialStatus socialStatus; // Assuming SocialStatus is an enum in your domain

    /**
     * The cause of death for the person. Assumed to be an enumeration (e.g., CauseOfDeath.NATURAL, CauseOfDeath.ACCIDENT).
     */
    private CauseOfDeath causeOfDeath; // Assuming CauseOfDeath is an enum in your domain

    /**
     * A list of occupations associated with the person.
     * Transferred as a list of PersonOccupationDTOs, implying that the frontend
     * requires details of each occupation directly within the Person's data.
     */
    private List<PersonOccupationDTO> occupations; // Assuming PersonOccupationDTO is another DTO

    /**
     * A list of source evidences related to the person's information.
     * Transferred as a list of PersonSourceEvidenceDTOs.
     */
    private List<PersonSourceEvidenceDTO> sourceEvidences; // Assuming PersonSourceEvidenceDTO is another DTO

    /**
     * The year of birth. Can be null if only month/day or no birth date information is available.
     * For full date representation and validation (e.g., "February 30th"),
     * conversion to {@link java.time.LocalDate} should occur in the service layer or entity.
     */
    private Integer birthYear;

    /**
     * The month of birth (1-12). Can be null if only the year is known.
     */
    private Integer birthMonth;

    /**
     * The day of birth (1-31). Can be null if only the year or month is known.
     */
    private Integer birthDay;

    /**
     * The year of baptization. Can be null.
     */
    private Integer baptizationYear;

    /**
     * The month of baptization. Can be null.
     */
    private Integer baptizationMonth;

    /**
     * The day of baptization. Can be null.
     */
    private Integer baptizationDay;

    /**
     * The year of death. Can be null.
     */
    private Integer deathYear;

    /**
     * The month of death. Can be null.
     */
    private Integer deathMonth;

    /**
     * The day of death. Can be null.
     */
    private Integer deathDay;

    /**
     * The year of burial. Can be null.
     */
    private Integer burialYear;

    /**
     * The month of burial. Can be null.
     */
    private Integer burialMonth;

    /**
     * The day of burial. Can be null.
     */
    private Integer burialDay;
}
