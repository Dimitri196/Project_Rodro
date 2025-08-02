package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParishDTO {

    @JsonProperty("_id")
    private Long id;

    private String parishName;
    private String parishMainChurchName;

    private Integer establishmentYear;

    private Integer cancellationYear;

    private LocationDTO parishLocation;
}
