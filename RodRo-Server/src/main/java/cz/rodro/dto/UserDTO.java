package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {

    @JsonProperty("_id")
    private Long userId;

    @NotBlank(message = "Full name must be filled in")
    private String fullName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email must be filled in")
    private String email;

    @NotBlank(message = "Password must be filled in")
    @Size(min = 6, message = "The password must be at least 6 characters long")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty("isAdmin")
    private boolean admin;
}
