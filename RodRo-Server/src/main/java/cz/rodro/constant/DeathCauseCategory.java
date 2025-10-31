package cz.rodro.constant;

import lombok.Getter;

@Getter
public enum DeathCauseCategory {

    INFECTIOUS_AND_PARASITIC("Infectious and Parasitic Diseases: Tuberculosis, Cholera, Measles, COVID-19, etc."),
    CANCER_AND_TUMORS("Cancers and Tumors: Cancer, Body Tumor, etc."),
    CARDIOVASCULAR_AND_STROKE("Cardiovascular Diseases and Stroke: Heart Disease, Stroke, Hypertension, etc."),
    RESPIRATORY_SYSTEM("Respiratory System Diseases: Pneumonia, Asthma, Croup, etc."),
    DIGESTIVE_AND_URINARY("Digestive and Urinary Disorders: Kidney Disease, Liver Disease, Hernia, etc."),
    MENTAL_AND_NEUROLOGICAL("Mental and Neurological Disorders: Epilepsy, Dementia, Melancholia, etc."),
    EXTERNAL_AND_INJURY("External Causes and Injury: Accident, Suicide, Homicide, War Wounds, etc."),
    MATERNAL_AND_PERINATAL("Maternal and Perinatal Causes: Maternal Deaths, Perinatal Complications, etc."),
    CHRONIC_NON_COMMUNICABLE("Chronic Non-Communicable Diseases: Diabetes, Alcoholism, Starvation, etc."),
    HISTORICAL_AND_VAGUE("Historical and Vague Causes: Old Age, Dropsy, Fever, Spanish Flu, etc."),
    UNKNOWN("Unknown/Other");

    private final String displayName;

    DeathCauseCategory(String displayName) {
        this.displayName = displayName;
    }

}
