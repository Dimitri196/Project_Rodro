package cz.rodro.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
/**
 * Enum representing the different levels of military ranks.
 * Each level has a name and a description to clarify its role within the military structure.
 */
public enum MilitaryRankLevel {

    // Defines the primary leadership and command structure
    OFFICER("Officer", "Commissioned officers hold leadership positions, manage units, and are responsible for strategic decisions."),

    // Defines the bridge between commissioned officers and junior enlisted personnel
    NCO("Non-Commissioned Officer", "NCOs are a group of enlisted personnel who have been promoted to a position of authority and responsibility."),

    // Defines the foundation of the military force
    ENLISTED("Enlisted", "Enlisted personnel are the backbone of the military, performing the daily tasks and duties of their specific role.");

    private final String name;
    private final String description;

    /**
     * Returns a string representation of the military rank level.
     *
     * @return A string containing the name and description of the rank level.
     */
    @Override
    public String toString() {
        return name + ": " + description;
    }
}
