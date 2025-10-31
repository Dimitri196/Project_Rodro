package cz.rodro.dto;

public record PersonInLocationDTO(
        Long id,
        String givenName,
        String surname,
        Integer birthYear,
        Integer deathYear
) {}