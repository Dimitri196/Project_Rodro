package cz.rodro.constant;

import lombok.Getter;

@Getter
public enum SocialStatus {
    BOURGEOIS("Mieszczanin"),
    NOBILITY("Szlachcic"),
    PEASANT("Włościanin"),
    CLERGY("Duchowny"),
    UNKNOWN("Status nieznany"),
    NOT_APPLICABLE("Nie ma zastosowania");

    private final String displayName;

    SocialStatus(String displayName) {
        this.displayName = displayName;
    }

}
