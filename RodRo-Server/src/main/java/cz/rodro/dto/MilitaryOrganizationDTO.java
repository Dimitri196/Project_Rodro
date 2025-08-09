package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MilitaryOrganizationDTO {

    @JsonProperty("_id")
    private Long id;

    private String armyName;      // e.g., "Prussian Army"
    private MilitaryArmyBranchDTO armyBranch;
    private CountryDTO country;   // Country info
    private String activeFromYear;
    private String activeToYear;

    // Use MilitaryStructureSimpleDTO to break the circular dependency
    private List<MilitaryStructureSimpleDTO> structures;
}
