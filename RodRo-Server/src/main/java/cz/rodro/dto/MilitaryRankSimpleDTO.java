package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Simple Data Transfer Object for embedding a Military Rank into other structures,
 * providing minimal required detail for identification and sorting.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MilitaryRankSimpleDTO {

    @JsonProperty("id") // Use 'id' for consistency
    private Long id;

    /**
     * The official name of the rank (e.g., "Sergeant", "General").
     */
    private String name;

    /**
     * A numerical value defining the rank's hierarchy level (lower number = lower rank).
     */
    private String rankLevel;
}
