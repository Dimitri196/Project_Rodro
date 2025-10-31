package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleReadDTO {

    @JsonProperty("_id")
    private Long id;

    private String title;
    private String description;

    // --- NEW FIELDS REQUIRED BY FRONTEND ---

    // The display name of the User/Author (e.g., "Dr. A. Karskiego")
    @JsonProperty("author")
    private String authorName;

    // Used for sorting and display
    private Long views;

    // Used for tags display
    private List<String> categories;

    // Used for sorting and date display
    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    // Full content is required for the detail view (ArticleDetail)
    private String content;

}
