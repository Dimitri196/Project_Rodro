package cz.rodro.constant;

import lombok.Getter;

@Getter
public enum CauseOfDeath {
    // Choroby zakaźne
    TUBERCULOSIS("Gruźlica"),
    CHOLERA("Cholera"),
    SMALLPOX("Ospa prawdziwa"),
    MEASLES("Odra"),
    MUMPS("Świnka"),
    PNEUMONIA("Zapalenie płuc"),
    INFLUENZA("Grypa"),
    MALARIA("Malaria"),
    PLAGUE("Dżuma"),
    TYPHUS("Tyfus plamisty"),
    TYPHOID_FEVER("Dur brzuszny"),
    LEPROSY("Trąd"),
    DYSENTERY("Czerwonka"),
    SCARLET_FEVER("Szkarlatyna"),
    DIPHTHERIA("Błonica"),
    POLIO("Polio"),
    RABIES("Wścieklizna"),
    HEPATITIS_A("WZW typu A"),
    HEPATITIS_B("WZW typu B"),
    HEPATITIS_C("WZW typu C"),
    SYPHILIS("Kiła"),
    GONORRHEA("Rzeżączka"),
    HIV_AIDS("HIV/AIDS"),
    COVID_19("COVID-19"),

    // Choroby dziedziczne i przewlekłe
    DIABETES("Cukrzyca"),
    CANCER("Nowotwór"),
    ASTHMA("Astma"),
    HYPERTENSION("Nadciśnienie"),
    STROKE("Udar mózgu"),
    HEART_DISEASE("Choroba serca"),
    ALZHEIMERS_DISEASE("Choroba Alzheimera"),
    PARKINSONS_DISEASE("Choroba Parkinsona"),

    // Zaburzenia psychiczne i neurologiczne
    MELANCHOLIA("Melancholia"),
    HYSTERIA("Histeria"),
    EPILEPSY("Padaczka"),
    DEMENTIA("Demencja"),
    SCHIZOPHRENIA("Schizofrenia"),
    MANIA("Mania"),
    SUICIDE("Samobójstwo"),

    // Czynniki środowiskowe i zawodowe
    BLACK_LUNG_DISEASE("Pylica węglowa"),
    SILICOSIS("Pylica krzemowa"),
    LEAD_POISONING("Zatrucie ołowiem"),
    ASBESTOSIS("Azbestoza"),
    STARVATION("Głód"),
    DEHYDRATION("Odwodnienie"),

    // Substancje i uzależnienia
    ALCOHOLISM("Alkoholizm"),
    DRUG_OVERDOSE("Przedawkowanie narkotyków"),
    TOBACCO_RELATED_DISEASE("Choroba związana z paleniem"),

    // Śmierć w wyniku przemocy lub wypadku
    ACCIDENT("Wypadek"),
    FALL("Upadek"),
    DROWNING("Utonięcie"),
    BURNS("Poparzenia"),
    HOMICIDE("Zabójstwo"),
    WAR_WOUNDS("Rany wojenne"),
    COMBAT_DEATH("Śmierć w walce"),
    EXECUTION("Egzekucja"),
    TERRORIST_ATTACK("Atak terrorystyczny"),

    // Inne/Historyczne
    OLD_AGE("Starość"),
    CONSUMPTION("Suchoty"),
    DROPSY("Puchlina wodna"),
    ERGOTISM("Zatrucie sporyszem"),
    SPANISH_FLU("Hiszpanka"),
    ENGLISH_SWEATING_SICKNESS("Angielska potówka"),
    FEVER("Gorączka"),
    UNKNOWN("Nieznana przyczyna"),
    OTHER("Inna przyczyna");

    private final String displayName;

    CauseOfDeath(String displayName) {
        this.displayName = displayName;
    }
}

