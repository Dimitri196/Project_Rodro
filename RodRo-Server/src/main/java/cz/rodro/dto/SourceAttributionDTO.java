package cz.rodro.dto;

import cz.rodro.constant.AttributionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SourceAttributionDTO {

    private Long id;
    private Long sourceId;

    private Long personId;
    private Long occupationId;
    private Long familyId;
    private Long militaryServiceId;

    private AttributionType type;
    private String note;
}
