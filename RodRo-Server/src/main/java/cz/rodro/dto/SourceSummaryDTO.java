package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SourceSummaryDTO {

    @JsonProperty("_id")
    private Long id;

    // The main display field for the source, e.g., the title or a short citation.
    private String title;

    /** Optional: Include the reference/short citation for better context. */
    private String reference;
}