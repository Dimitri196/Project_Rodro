package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDisplayDTO {

    @JsonProperty("userId")
    private long userId;

    @JsonProperty("authorName")
    private String fullName;

    private String email;

    @JsonProperty("isAdmin")
    private boolean admin;
}
