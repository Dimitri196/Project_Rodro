package cz.rodro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarriageDTO {

    private String spouse;               // Spouse ID (string)
    private List<String> children;       // List of children IDs
}
