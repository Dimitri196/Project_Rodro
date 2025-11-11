package cz.rodro.constant;

import lombok.Getter;

@Getter
public enum ConfessionType {

    CATHOLIC_LATIN("Roman Catholic (Latin)"),
    CATHOLIC_UNIATE("Greek Catholic / Uniate"),
    ORTHODOX("Eastern Orthodox"),
    PROTESTANT_LUTHERAN("Lutheran / Evangelical"),
    PROTESTANT_REFORMED("Calvinist / Reformed"),
    ANGLICAN("Anglican"),
    JEWISH("Jewish"),
    ISLAMIC("Islamic (Muslim)"),
    OTHER("Other / Unknown");

    // Field to hold the human-readable name
    private final String displayName;

    /**
     * Constructor for the enum, setting the display name.
     * @param displayName The user-friendly name of the confession type.
     */
    ConfessionType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Getter method required by the MapStruct mapper.
     * @return The user-friendly display name.
     */
    public String getDisplayName() {
        return displayName;
    }
}