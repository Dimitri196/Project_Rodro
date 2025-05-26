package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MilitaryOrganizationDTO {

    @JsonProperty("_id")
    private Long id;

    private String armyName;      // e.g., "Prussian Army"
    private String armyBranch;    // e.g., "Infantry"
    private CountryDTO country;   // Country info
    private String activeFromYear;
    private String activeToYear;
}
