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

    @JsonProperty("userId") // Use consistent casing, e.g., userId instead of _id here
    private long userId;

    // The key field for professional display in the frontend
    @JsonProperty("authorName")
    private String fullName;

    private String email;

    // Optional: Useful for frontend logic (e.g., displaying admin tools)
    @JsonProperty("isAdmin")
    private boolean admin;
}
