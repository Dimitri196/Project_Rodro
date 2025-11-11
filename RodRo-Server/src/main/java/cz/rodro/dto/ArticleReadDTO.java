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

    @JsonProperty("author")
    private String authorName;

    private Long views;

    private List<String> categories;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    private String content;

}
