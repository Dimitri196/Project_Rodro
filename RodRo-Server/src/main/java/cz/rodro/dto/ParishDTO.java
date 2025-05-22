package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParishDTO {

    @JsonProperty("_id")
    private Long id;

    private String parishName;
    private String parishMainChurchName;
    private LocalDate establishmentDate;
    private LocalDate cancellationDate;

    private LocationDTO parishLocation; // Correct, use LocationDTO to transfer location details to frontend
}