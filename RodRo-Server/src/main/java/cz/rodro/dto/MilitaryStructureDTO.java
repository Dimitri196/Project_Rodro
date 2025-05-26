package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MilitaryStructureDTO {

    @JsonProperty("_id")
    private Long id;

    private String unitName;                 // e.g., "3rd Infantry Regiment"
    private String unitType;                 // e.g., "Regiment"
    private MilitaryOrganizationDTO organization; // Reference to army/branch/country
    private String activeFromYear;
    private String activeToYear;
    private String notes;
}
