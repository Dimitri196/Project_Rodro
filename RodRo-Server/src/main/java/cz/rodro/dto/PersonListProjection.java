package cz.rodro.dto;

public interface PersonListProjection {

    Long getId();
    String getGivenName();
    String getSurname();
    String getExternalId();

    Integer getBirthYear();
    Integer getBirthMonth();
    Integer getBirthDay();

    Integer getDeathYear();
    Integer getDeathMonth();
    Integer getDeathDay();

    String getBirthPlaceName();
    String getBaptismPlaceName();
    String getDeathPlaceName();
    String getBurialPlaceName();

    default String getFullName() {
        return (getGivenName() != null ? getGivenName() : "") + " " +
                (getSurname() != null ? getSurname() : "");
    }
}