package cz.rodro.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTreeNodeDTO {

    private String id;                        // Person ID as string
    private String name;                      // Full name
    private String className;                 // "male" or "female"
    private String extra;                     // Optional: birth/death info
    private String textClass = "emphasis";    // For optional styling
    private List<MarriageDTO> marriages = new ArrayList<>();
}
