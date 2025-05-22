package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import cz.rodro.constant.MaritalStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamilyDTO {

    @JsonProperty("_id")
    private Long id;

    @NotNull
    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate marriageDate;

    private LocationDTO marriageLocation;

    @NotNull
    private PersonDTO spouseMale;
    private MaritalStatus maritalStatusForSpouseMale;

    @NotNull
    private PersonDTO spouseFemale;
    private MaritalStatus maritalStatusForSpouseFemale;

    private PersonDTO witnessesMaleSide1;
    private PersonDTO witnessesMaleSide2;
    private PersonDTO witnessesFemaleSide1;
    private PersonDTO witnessesFemaleSide2;

    private String source;

    @Size(max = 500)
    private String note;
}