package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MilitaryArmyBranchDTO {

    @JsonProperty("_id")
    private Long id;

    private String armyBranchName; // e.g. "Infantry", "Artillery"
}
