package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {

    @JsonProperty("_id")
    private Long id;

    @NotBlank(message = "Enter a title")
    @Size(max = 100, message = "Title is too long")
    private String title;

    @NotBlank(message = "Enter a description")
    private String description;

    @NotBlank(message = "Enter content")
    private String content;

}