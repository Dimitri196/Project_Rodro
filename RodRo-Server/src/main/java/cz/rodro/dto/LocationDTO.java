package cz.rodro.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import cz.rodro.constant.SettlementType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDTO {

    @JsonProperty("_id")
    private Long id;

    private String locationName;
    private String establishmentDate;
    private SettlementType settlementType;
    private String gpsLatitude;
    private String gpsLongitude;

    private List<LocationHistoryDTO> locationHistory;

}
