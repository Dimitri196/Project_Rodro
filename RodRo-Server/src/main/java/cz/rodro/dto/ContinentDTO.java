package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object (DTO) for Continent information.
 * Used for transferring basic continent data.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContinentDTO {

    /**
     * The unique identifier for the Continent.
     */
    @JsonProperty("_id")
    private Long id;

    /**
     * The primary geographical name of the continent (e.g., "Asia", "Europe").
     */
    @NotNull(message = "Continent name is required.")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters.")
    private String name;

    /**
     * The name of the continent in Polish for display purposes.
     */
    @Size(max = 50)
    private String nameInPolish;

}
