package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonMilitaryServiceDTO {

    @JsonProperty("_id")
    private Long id;

    private Long personId;              // FK to person record
    private Long militaryStructureId;  // FK to MilitaryStructureDTO
    private Long militaryOccupationId; // FK to MilitaryOccupationDTO

    private String enlistmentYear;
    private String dischargeYear;

    private String notes;

    private List<SourceDTO> sources;   // List of related source documents (not just IDs) or private List<Long> sourceIds; (just IDs)
}