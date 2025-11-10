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
    private String activeFromYear;
    private String activeToYear;
    private String notes;
    private String bannerImageUrl;

    // Added back to resolve the mapper error
    private String armyBranchName;

    // Link to the top-level organization using only IDs/Names (AVOIDS CIRCULAR REFERENCE)
    private Long organizationId;
    private String organizationName;

    // A list of ranks associated with this structure, which is populated by the service layer
    private List<MilitaryRankDTO> ranks;
}
