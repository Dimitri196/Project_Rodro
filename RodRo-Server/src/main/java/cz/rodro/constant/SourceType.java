package cz.rodro.constant;

import lombok.Getter;

/**
 * Enumeration of possible source types.
 *
 * <p>Represents the classification of a source, such as archival documents,
 * church records, census data, books, websites, etc.</p>
 *
 * <p>Each enum constant provides a {@code displayName} for use in user interfaces
 * where a human-readable form is preferred over the enum constant name.</p>
 */
@Getter
public enum SourceType {

    /** Historical records stored in archives. */
    ARCHIVAL_DOCUMENT("Archival Document"),

    /** Records maintained by churches (e.g., baptisms, marriages, burials). */
    CHURCH_RECORD("Church Record"),

    /** Civil registry documents such as birth, marriage, or death certificates. */
    CIVIL_REGISTRY("Civil Registry"),

    /** Population census records. */
    CENSUS("Census"),

    /** Records related to military service or enlistment. */
    MILITARY_RECORD("Military Record"),

    /** Taxation-related records. */
    TAX_RECORD("Tax Record"),

    /** Newspaper publications or articles. */
    NEWSPAPER("Newspaper"),

    /** Books, printed publications, or other literary works. */
    BOOK("Book / Publication"),

    /** Oral history accounts or interviews. */
    ORAL_HISTORY("Oral History"),

    /** Photographic evidence or images. */
    PHOTOGRAPH("Photograph"),

    /** Maps or cartographic records. */
    MAP("Map"),

    /** Legal documents such as contracts, wills, or court records. */
    LEGAL_DOCUMENT("Legal Document"),

    /** Personal letters, correspondence, or diaries. */
    PERSONAL_CORRESPONDENCE("Personal Correspondence"),

    /** Databases or digital sources. */
    DATABASE("Database / Digital Source"),

    /** Websites or online sources. */
    WEBSITE("Website"),

    /** Unknown source type. */
    UNKNOWN("Unknown"),

    /** Not applicable in the given context. */
    NOT_APPLICABLE("Not Applicable");

    /**
     * Human-readable label for the source type.
     * <p>Used in UI representations instead of the enum constant name.</p>
     */
    private final String displayName;

    SourceType(String displayName) {
        this.displayName = displayName;
    }
}
