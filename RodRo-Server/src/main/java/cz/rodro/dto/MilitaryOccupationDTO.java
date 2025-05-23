package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MilitaryOccupationDTO {

    @JsonProperty("_id")
    private Long id;

    private String occupationName; // e.g., "Lieutenant", "Rotmistrz", "Standard-bearer"
    private String description;    // Description or notes about the role

    private String rankLevel;      // Optional: rank seniority or classification (e.g., "Officer", "NCO", "Enlisted")

    private Long militaryStructureId;  // FK linking to MilitaryStructureDTO.id

    private String activeFromYear; // Time frame when this occupation existed (optional)
    private String activeToYear;

    private String notes;          // Additional remarks

}