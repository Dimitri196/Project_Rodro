package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonOccupationDTO {

    @JsonProperty("_id")
    private Long id;

    private Long personId;
    private Long occupationId;

    @Min(value = 0, message = "Establishment year cannot be negative")
    @Max(value = 2025, message = "Establishment year cannot exceed 9999")
    private Integer startYear;

    @Min(value = 0, message = "Establishment year cannot be negative")
    @Max(value = 2025, message = "Establishment year cannot exceed 9999")
    private Integer endYear;

    // new fields
    private Long institutionId;
    private String occupationName;
    private String institutionName;

    private Long institutionLocationId;
    private String institutionLocationName;

    private String givenName;
    private String givenSurname;


}
