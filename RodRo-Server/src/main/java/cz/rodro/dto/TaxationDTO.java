package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaxationDTO {

    @JsonProperty("_id")
    private Long id;

    private String taxName;

    private String taxDescription;

}
