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

    private Long personId;

    private MilitaryStructureDTO militaryStructure; // Reference to structure DTO
    private MilitaryRankDTO militaryRank;           // Reference to rank DTO

    private String givenName;
    private String givenSurname;

    private String militaryStructureName; // Optional: for frontend display
    private String armyBranch;            // Optional: for frontend display
    private String rankName;              // Optional: for frontend display

    private String enlistmentYear;
    private String dischargeYear;

    private String notes;

    private List<SourceDTO> sources;
}