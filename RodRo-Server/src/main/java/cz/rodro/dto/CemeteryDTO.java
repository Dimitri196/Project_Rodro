package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CemeteryDTO {

    @JsonProperty("_id")
    private Long id;
    private LocationDTO cemeteryLocation;
    private String cemeteryName;
    private String description;
    private String webLink;

}
