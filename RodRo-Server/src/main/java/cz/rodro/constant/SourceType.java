package cz.rodro.constant;

import lombok.Getter;

@Getter
public enum SourceType {
    ARCHIVAL_DOCUMENT("Archival Document"),
    CHURCH_RECORD("Church Record"),
    CIVIL_REGISTRY("Civil Registry"),
    CENSUS("Census"),
    MILITARY_RECORD("Military Record"),
    TAX_RECORD("Tax Record"),
    NEWSPAPER("Newspaper"),
    BOOK("Book / Publication"),
    ORAL_HISTORY("Oral History"),
    PHOTOGRAPH("Photograph"),
    MAP("Map"),
    LEGAL_DOCUMENT("Legal Document"),
    PERSONAL_CORRESPONDENCE("Personal Correspondence"),
    DATABASE("Database / Digital Source"),
    WEBSITE("Website"),
    UNKNOWN("Unknown"),
    NOT_APPLICABLE("Not Applicable");

    private final String displayName;

    SourceType(String displayName) {
        this.displayName = displayName;
    }
}
