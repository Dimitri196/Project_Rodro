package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OccupationDTO {

    @JsonProperty("_id")
    @NotNull
    private Long id;
    private String occupationName;
    private String description;
    private InstitutionDTO institution;
    private List<PersonOccupationDTO> personOccupations;

}