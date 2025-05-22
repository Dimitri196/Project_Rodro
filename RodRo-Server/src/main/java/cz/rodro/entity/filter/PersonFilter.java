package cz.rodro.entity.filter;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PersonFilter {

    private String firstName;
    private String lastName;

    private Long personBirthLocationId;
    private Long personBaptizationLocationId;
    private Long personDeathLocationId;
    private Long personBurialLocationId;

    private LocalDate personBirthDate;
    private LocalDate personDeathDate;
    private LocalDate personBurialDate;
    private LocalDate personBaptizationDate;

    private Integer limit;
}
