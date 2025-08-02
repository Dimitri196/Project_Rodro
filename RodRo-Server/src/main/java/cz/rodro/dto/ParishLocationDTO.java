package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParishLocationDTO {

    @JsonProperty("_id")
    private Long id;

    private Long parishId;
    private Long locationId;

    private String parishName;
    private String locationName;

}
