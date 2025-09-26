package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.rodro.constant.ConfessionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object for Parish entity.
 * Used for transferring parish data between backend and frontend.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParishDTO {

    /** Unique identifier of the parish. Serialized as "_id" for frontend consistency. */
    @JsonProperty("_id")
    private Long id;

    /** Name of the parish. */
    private String name;

    /** Main church of the parish. */
    private String mainChurchName;

    /** Year the parish was established (nullable). */
    private Integer establishmentYear;

    /** Year the parish was cancelled or dissolved (nullable). */
    private Integer cancellationYear;

    /** Main location of the parish. Can be null if unknown. */
    private LocationDTO location;

    /** List of all associated locations (including main location). */
    private List<ParishLocationDTO> locations;

    /** URL pointing to an image of the parish's main church. */
    private String churchImageUrl;

    /** Description of the parish. */
    private String description;

    /** Religious confession/denomination of this parish. */
    private ConfessionType confession;
}
