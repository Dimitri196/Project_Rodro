package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MilitaryRankDTO {

    @JsonProperty("_id")
    private Long id;

    private String rankName; // e.g., "Major", "General"
    private String rankDescription; // Description or notes about the role
    private String rankLevel; // e.g., "Officer", "NCO", "Enlisted"
    private MilitaryOrganizationDTO organization; // Reference to army/branch/country
    private String activeFromYear; // Optional: when this rank existed
    private String activeToYear;
    private String notes; // Additional remarks
}