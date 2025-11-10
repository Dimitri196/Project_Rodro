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

    // Primary ID for the Service record instance itself
    @JsonProperty("id")
    private Long id;

    // --- References (Use Simple DTOs/IDs) ---

    // Reference to the Person (REQUIRED for context if not nested)
    private Long personId;

    // Reference to structure (Use simple DTO or IDs/Names)
    private MilitaryStructureDTO militaryStructure;

    // Reference to rank (Use simple DTO)
    private MilitaryRankDTO militaryRank;

    // --- Denormalized Data (For quick display) ---
    // These fields are populated by the service layer using the relationship IDs
    private String personGivenName; // Renamed for clarity
    private String personSurname;   // Renamed for clarity

    private String militaryStructureName; // Redundant if MilitaryStructureSimpleDTO is used, but kept if preferred
    private String armyBranchName;        // Renamed from armyBranch for clarity
    private String rankName;              // Redundant if MilitaryRankSimpleDTO is used, but kept if preferred

    // --- Service Details ---
    @Min(value = 0, message = "Enlistment year cannot be negative")
    @Max(value = 9999, message = "Enlistment year cannot exceed 9999") // Changed max value
    private Integer enlistmentYear;

    @Min(value = 0, message = "Discharge year cannot be negative")
    @Max(value = 9999, message = "Discharge year cannot exceed 9999")
    private Integer dischargeYear;

    private String notes;

    private List<SourceDTO> sources;
}