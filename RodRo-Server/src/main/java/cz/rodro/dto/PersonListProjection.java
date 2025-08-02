package cz.rodro.dto;

public interface PersonListProjection {

    Long getId();
    String getGivenName();
    String getGivenSurname();
    String getIdentificationNumber();

    // Partial Dates
    Integer getBirthYear();
    Integer getBirthMonth();
    Integer getBirthDay();

    Integer getDeathYear();
    Integer getDeathMonth();
    Integer getDeathDay();

    // Flattened related entity names - IMPORTANT: These need specific query methods
    String getBirthPlaceName(); // This would map to person.birthPlace.locationName
    String getBaptizationPlaceName(); // This would map to person.baptizationPlace.locationName
    String getDeathPlaceName(); // This would map to person.deathPlace.locationName
    String getBurialPlaceName(); // This would map to person.burialPlace.locationName

    // Example of how to get a full name if needed in the projection (derived property)
    default String getFullName() {
        return (getGivenName() != null ? getGivenName() : "") + " " +
                (getGivenSurname() != null ? getGivenSurname() : "");
    }
}