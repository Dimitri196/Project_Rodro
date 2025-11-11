package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object for a Person's Military Service record.
 * Uses simple DTOs for relationships to prevent circular dependencies and excessive data loading.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonMilitaryServiceDTO {

    @JsonProperty("id")
    private Long id;

    private Long personId;

    private MilitaryStructureDTO militaryStructure;

    private MilitaryRankDTO militaryRank;

    private String personGivenName; // Renamed for clarity
    private String personSurname;   // Renamed for clarity

    private String militaryStructureName;
    private String armyBranchName;
    private String rankName;

    @Min(value = 0, message = "Enlistment year cannot be negative")
    @Max(value = 9999, message = "Enlistment year cannot exceed 9999") // Changed max value
    private Integer enlistmentYear;

    @Min(value = 0, message = "Discharge year cannot be negative")
    @Max(value = 9999, message = "Discharge year cannot exceed 9999")
    private Integer dischargeYear;

    private String notes;

    private List<SourceDTO> sources;
}