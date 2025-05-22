package cz.rodro.entity.filter;

import cz.rodro.constant.MaritalStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FamilyFilter {
    private Long marriageLocationID;
    private Long spouseMaleID;
    private Long spouseFemaleID;
    private MaritalStatus maritalStatusForSpouseMale;
    private MaritalStatus maritalStatusForSpouseFemale;
    private LocalDate marriageDate;
    private String note;
    /**
     * The maximum number of families to return.
     * The default value is 10.
     */
    private Integer limit = 10;
}