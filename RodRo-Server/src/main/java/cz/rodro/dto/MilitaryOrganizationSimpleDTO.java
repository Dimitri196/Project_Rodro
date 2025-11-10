package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Simple Data Transfer Object for embedding a Military Organization into other structures.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MilitaryOrganizationSimpleDTO {

    @JsonProperty("id")
    private Long id;

    /**
     * The official name of the organization (e.g., "Prussian Army", "Imperial Japanese Army").
     */
    private String name;
}
