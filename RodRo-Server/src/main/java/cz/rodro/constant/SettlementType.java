package cz.rodro.constant;

import lombok.Getter;

@Getter
public enum SettlementType {
    CITY("City"),
    TOWN("Town"),
    VILLAGE("Village"),
    FARM("Farm"),
    FORESTERS_LODGE ("Forester's lodge"),
    KHUTOR ("Khutor"),
    SETTLEMENT("Settlement"),
    COLONY("Colony"),
    RAILWAY_STATION("Railway Station"),
    BRICKYARD("Brickyard"),
    MILL_SETTLEMENT("Mill Settlement"),
    ZASCIANEK("Zaścianek"),
    FOREST_SETTLEMENT("Forest Settlement"),
    FACTORY_SETTLEMENT("Factory Settlement"),
    SAWMILL("Sawmill"),
    SUBURB("Suburb"),
    TAR_FACTORY("Tar Factory");

    private final String displayName;

    SettlementType(String displayName) {
        this.displayName = displayName;
    }

}