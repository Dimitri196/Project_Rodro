package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonMilitaryServiceDTO {

    private Long personId;

    private MilitaryStructureDTO militaryStructure; // Reference to structure DTO
    private MilitaryRankDTO militaryRank;           // Reference to rank DTO

    private String givenName;
    private String givenSurname;

    private String militaryStructureName; // Optional: for frontend display
    private String armyBranch;            // Optional: for frontend display
    private String rankName;              // Optional: for frontend display

    @Min(value = 0, message = "Establishment year cannot be negative")
    @Max(value = 2025, message = "Establishment year cannot exceed 9999")
    private Integer enlistmentYear;

    @Min(value = 0, message = "Establishment year cannot be negative")
    @Max(value = 2025, message = "Establishment year cannot exceed 9999")
    private Integer dischargeYear;

    private String notes;

    private List<SourceDTO> sources;
}
