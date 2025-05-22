package cz.rodro.entity.filter;

import lombok.Data;

@Data
public class LocationFilter {
    private String name;
    private String district;
    private String region;
    private String country;
}
