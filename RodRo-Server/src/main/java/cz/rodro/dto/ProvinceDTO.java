package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProvinceDTO {

    @JsonProperty("_id")
    private Long id;

    private String name;

    private Long countryId;

    private String countryName;

    private List<DistrictDTO> districts;

    private String imgUrl;
}
