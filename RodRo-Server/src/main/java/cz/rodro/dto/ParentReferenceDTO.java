package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.rodro.constant.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentReferenceDTO {

    @JsonProperty("_id")
    private Long id;
    private String givenName;
    private String givenSurname;
    private Gender gender;
}