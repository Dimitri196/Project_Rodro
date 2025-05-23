package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MilitaryStructureDTO {

    @JsonProperty("_id")
    private Long id;

    private String armyName;       // e.g., "Crown Army", "Prussian Army", "Soviet Army"
    private String armyBranch;     // Infantry, Cavalry, Artillery, Navy, Air Force, etc.
    private String unitName;       // e.g., "Chorągiew husarska", "3rd Infantry Regiment"
    private String unitType;       // Regiment, Battalion, Company, Squadron, etc.

    private CountryDTO country;    // Country info: id, name, historical period, etc.

    private String activeFromYear; // Approximate start date of unit's existence or time frame
    private String activeToYear;   // Approximate end date or disbandment year

    private String notes;          // Additional info or remarks

}
