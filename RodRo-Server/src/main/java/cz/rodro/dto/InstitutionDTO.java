package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionDTO {

    @JsonProperty("_id")
    @NotNull
    private Long id;

    private String institutionName;
    private String institutionDescription;
    private LocationDTO institutionLocation;
    private List<OccupationDTO> occupations;

}
