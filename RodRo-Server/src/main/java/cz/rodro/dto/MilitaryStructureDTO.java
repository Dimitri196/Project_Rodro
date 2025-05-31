package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MilitaryStructureDTO {

    @JsonProperty("_id")
    private Long id;

    private String unitName;
    private String unitType;
    private MilitaryOrganizationDTO organization; // 👈 Use this instead of full DTO to avoid recursion
    private String activeFromYear;
    private String activeToYear;
    private String notes;
    private String armyBranchName;

}
