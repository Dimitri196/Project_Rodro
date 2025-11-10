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

    private String name; // e.g., "Major", "General"
    private String description; // Description or notes about the role
    private MilitaryRankLevel rankLevel; // Enum representing the level of the rank (e.g., Officer, NCO, Enlisted)
    private String activeFromYear; // Optional: when this rank existed
    private String activeToYear;
    private String notes; // Additional remarks

    private List<PersonMilitaryServiceDTO> persons; // List of persons holding this rank

    /**
     * The ID of the organization this rank belongs to.
     */
    private Long organizationId;

    private String organizationName;

    private String structureName;
    private String structureId;

    private String insigniaImageUrl;
}
