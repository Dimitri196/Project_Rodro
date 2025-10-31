package cz.rodro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleWriteDTO {

    @NotBlank(message = "Enter a title")
    @Size(max = 100, message = "Title is too long")
    private String title;

    @NotBlank(message = "Enter a description")
    @Size(max = 255, message = "Description is too long")
    private String description;

    @NotBlank(message = "Enter content")
    private String content;

    // User submits the tags as a list of strings
    private List<String> categories;
}
