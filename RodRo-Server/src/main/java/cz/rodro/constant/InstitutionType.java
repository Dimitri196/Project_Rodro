package cz.rodro.constant;

public enum InstitutionType {

    POLITICAL("Political institutions shaped sovereignty and governance."),
    ECONOMIC("Economic institutions organized trade, credit, and crafts."),
    SOCIAL("Social institutions provided welfare, solidarity, and charity."),
    CULTURAL("Cultural institutions sustained arts and identity."),
    EDUCATIONAL("Universities and schools trained elites and circulated knowledge."),
    RELIGIOUS("Monasteries and orders preserved traditions and spiritual life."),
    ARCHIVAL("Archives and libraries safeguarded memory and law."),
    SCIENTIFIC("Scientific societies institutionalized research and exchange."),
    MILITARY("Military institutions organized armies, defense, and strategy.");

    private final String description;

    InstitutionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
