package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonSourceEvidenceDTO {

    @JsonProperty("_id")
    private Long id;

    private Long personId;
    private Long sourceId;

    private String personName;
    private String sourceName;

}