package cz.rodro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for displaying a source in a list view (paginated).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SourceListDTO {

    private Long id;
    private String title;
    private String reference;
    private String description;
    private String url;

    private String type;

    private String confession;

    private Integer creationYear;
    private Integer startYear;
    private Integer endYear;

    private Long locationId;
    private String locationName;

}
