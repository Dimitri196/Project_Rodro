package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.rodro.constant.MilitaryRankLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MilitaryRankDTO {

    @JsonProperty("_id")
    private Long id;

    private String rankName; // e.g., "Major", "General"
    private String rankDescription; // Description or notes about the role
    private MilitaryRankLevel rankLevel; // Enum representing the level of the rank (e.g., Officer, NCO, Enlisted)
    private String activeFromYear; // Optional: when this rank existed
    private String activeToYear;
    private String notes; // Additional remarks

    private List<PersonMilitaryServiceDTO> persons; // List of persons holding this rank

    private MilitaryOrganizationDTO militaryOrganization; // Link to the military organization this rank belongs to

    private MilitaryStructureDTO militaryStructureDTO;

    private String rankImageUrl;
}
