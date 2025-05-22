package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.rodro.constant.SourceType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for Source.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SourceDTO {

    @JsonProperty("_id")
    private Long id;

    @NotBlank
    private String sourceTitle;
    private String sourceDescription;
    private String sourceReference;
    private SourceType sourceType;
    private String sourceUrl;
}
