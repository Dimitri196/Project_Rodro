package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryDTO {

    @JsonProperty("_id")
    private Long id;

    @NotNull
    @Size(min = 2, max = 100)
    private String countryNameInPolish;

    @NotNull
    @Size(min = 2, max = 100)
    private String countryNameInEnglish;

    private String countryEstablishmentYear;
    private String countryCancellationYear;

    @JsonIgnore  // Prevents recursive serialization
    private List<ProvinceDTO> provinces;  // Add this if you want to return the list of provinces for the country
}
