package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    private LocalDate startDate;
    private LocalDate endDate;

    // new fields
    private Long institutionId;
    private String occupationName;
    private String institutionName;

    private Long institutionLocationId;
    private String institutionLocationName;

    private String givenName;
    private String givenSurname;


}
