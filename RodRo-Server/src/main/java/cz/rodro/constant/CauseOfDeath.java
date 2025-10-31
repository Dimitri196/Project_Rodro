package cz.rodro.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum CauseOfDeath {
    // ------------------------------------------------------------------------------------------------
    // INFECTIOUS_AND_PARASITIC
    // ------------------------------------------------------------------------------------------------
    TUBERCULOSIS("Tuberculosis", DeathCauseCategory.INFECTIOUS_AND_PARASITIC),
    CHOLERA("Cholera", DeathCauseCategory.INFECTIOUS_AND_PARASITIC),
    SMALLPOX("Smallpox", DeathCauseCategory.INFECTIOUS_AND_PARASITIC),
    MEASLES("Measles", DeathCauseCategory.INFECTIOUS_AND_PARASITIC),
    DIARRHEA("Diarrhea", DeathCauseCategory.INFECTIOUS_AND_PARASITIC),
    MUMPS("Mumps", DeathCauseCategory.INFECTIOUS_AND_PARASITIC),
    INFLUENZA("Influenza", DeathCauseCategory.INFECTIOUS_AND_PARASITIC),
    MALARIA("Malaria", DeathCauseCategory.INFECTIOUS_AND_PARASITIC),
    PLAGUE("Plague", DeathCauseCategory.INFECTIOUS_AND_PARASITIC),
    TYPHUS("Typhus", DeathCauseCategory.INFECTIOUS_AND_PARASITIC),
    TYPHOID_FEVER("Typhoid Fever", DeathCauseCategory.INFECTIOUS_AND_PARASITIC),
    LEPROSY("Leprosy", DeathCauseCategory.INFECTIOUS_AND_PARASITIC),
    DYSENTERY("Dysentery", DeathCauseCategory.INFECTIOUS_AND_PARASITIC),
    SCARLET_FEVER("Scarlet Fever", DeathCauseCategory.INFECTIOUS_AND_PARASITIC),
    DIPHTHERIA("Diphtheria", DeathCauseCategory.INFECTIOUS_AND_PARASITIC),
    POLIO("Polio", DeathCauseCategory.INFECTIOUS_AND_PARASITIC),
    RABIES("Rabies", DeathCauseCategory.INFECTIOUS_AND_PARASITIC),
    HEPATITIS_A("Hepatitis A", DeathCauseCategory.INFECTIOUS_AND_PARASITIC),
    HEPATITIS_B("Hepatitis B", DeathCauseCategory.INFECTIOUS_AND_PARASITIC),
    HEPATITIS_C("Hepatitis C", DeathCauseCategory.INFECTIOUS_AND_PARASITIC),
    SYPHILIS("Syphilis", DeathCauseCategory.INFECTIOUS_AND_PARASITIC),
    GONORRHEA("Gonorrhea", DeathCauseCategory.INFECTIOUS_AND_PARASITIC),
    HIV_AIDS("HIV/AIDS", DeathCauseCategory.INFECTIOUS_AND_PARASITIC),
    COVID_19("COVID-19", DeathCauseCategory.INFECTIOUS_AND_PARASITIC),
    INFECTIONS("Infections", DeathCauseCategory.INFECTIOUS_AND_PARASITIC),

    // ------------------------------------------------------------------------------------------------
    // CANCER_AND_TUMORS
    // ------------------------------------------------------------------------------------------------
    CANCER("Cancer", DeathCauseCategory.CANCER_AND_TUMORS),
    BODY_TUMOR("Body Tumor", DeathCauseCategory.CANCER_AND_TUMORS),

    // ------------------------------------------------------------------------------------------------
    // CARDIOVASCULAR_AND_STROKE
    // ------------------------------------------------------------------------------------------------
    HEART_DISEASE("Heart Disease", DeathCauseCategory.CARDIOVASCULAR_AND_STROKE),
    STROKE("Stroke", DeathCauseCategory.CARDIOVASCULAR_AND_STROKE),
    HYPERTENSION("Hypertension", DeathCauseCategory.CARDIOVASCULAR_AND_STROKE),
    APOPLEXY("Apoplexy", DeathCauseCategory.CARDIOVASCULAR_AND_STROKE),

    // ------------------------------------------------------------------------------------------------
    // RESPIRATORY_SYSTEM
    // ------------------------------------------------------------------------------------------------
    COUGHS_AND_RESPIRATORY_DISEASES("Coughs and Respiratory Diseases", DeathCauseCategory.RESPIRATORY_SYSTEM),
    PNEUMONIA("Pneumonia", DeathCauseCategory.RESPIRATORY_SYSTEM),
    CROUP("Croup", DeathCauseCategory.RESPIRATORY_SYSTEM),
    ASTHMA("Asthma", DeathCauseCategory.RESPIRATORY_SYSTEM),
    BLACK_LUNG_DISEASE("Black Lung Disease", DeathCauseCategory.RESPIRATORY_SYSTEM),
    SILICOSIS("Silicosis", DeathCauseCategory.RESPIRATORY_SYSTEM),
    ASBESTOSIS("Asbestosis", DeathCauseCategory.RESPIRATORY_SYSTEM),

    // ------------------------------------------------------------------------------------------------
    // DIGESTIVE_AND_URINARY
    // ------------------------------------------------------------------------------------------------
    STOMACH_AILMENTS("Stomach Ailments", DeathCauseCategory.DIGESTIVE_AND_URINARY),
    COLIC("Colic", DeathCauseCategory.DIGESTIVE_AND_URINARY),
    CONSTIPATION("Constipation", DeathCauseCategory.DIGESTIVE_AND_URINARY),
    LIVER_DISEASE("Liver Disease", DeathCauseCategory.DIGESTIVE_AND_URINARY),
    KIDNEY_DISEASE("Kidney Disease", DeathCauseCategory.DIGESTIVE_AND_URINARY),
    HERNIA("Hernia", DeathCauseCategory.DIGESTIVE_AND_URINARY),

    // ------------------------------------------------------------------------------------------------
    // MENTAL_AND_NEUROLOGICAL
    // ------------------------------------------------------------------------------------------------
    CONVULSIONS("Convulsions", DeathCauseCategory.MENTAL_AND_NEUROLOGICAL),
    MELANCHOLIA("Melancholia", DeathCauseCategory.MENTAL_AND_NEUROLOGICAL),
    HYSTERIA("Hysteria", DeathCauseCategory.MENTAL_AND_NEUROLOGICAL),
    EPILEPSY("Epilepsy", DeathCauseCategory.MENTAL_AND_NEUROLOGICAL),
    DEMENTIA("Dementia", DeathCauseCategory.MENTAL_AND_NEUROLOGICAL),
    SCHIZOPHRENIA("Schizophrenia", DeathCauseCategory.MENTAL_AND_NEUROLOGICAL),
    MANIA("Mania", DeathCauseCategory.MENTAL_AND_NEUROLOGICAL),
    ALZHEIMERS_DISEASE("Alzheimer's Disease", DeathCauseCategory.MENTAL_AND_NEUROLOGICAL),
    PARKINSONS_DISEASE("Parkinson's Disease", DeathCauseCategory.MENTAL_AND_NEUROLOGICAL),
    PARALYSIS("Paralysis", DeathCauseCategory.MENTAL_AND_NEUROLOGICAL),

    // ------------------------------------------------------------------------------------------------
    // EXTERNAL_AND_INJURY
    // ------------------------------------------------------------------------------------------------
    ACCIDENT("Accident", DeathCauseCategory.EXTERNAL_AND_INJURY),
    FALL("Fall", DeathCauseCategory.EXTERNAL_AND_INJURY),
    DROWNING("Drowning", DeathCauseCategory.EXTERNAL_AND_INJURY),
    BURNS("Burns", DeathCauseCategory.EXTERNAL_AND_INJURY),
    HOMICIDE("Homicide", DeathCauseCategory.EXTERNAL_AND_INJURY),
    WAR_WOUNDS("War Wounds", DeathCauseCategory.EXTERNAL_AND_INJURY),
    COMBAT_DEATH("Combat Death", DeathCauseCategory.EXTERNAL_AND_INJURY),
    EXECUTION("Execution", DeathCauseCategory.EXTERNAL_AND_INJURY),
    TERRORIST_ATTACK("Terrorist Attack", DeathCauseCategory.EXTERNAL_AND_INJURY),
    SUICIDE("Suicide", DeathCauseCategory.EXTERNAL_AND_INJURY),
    DRUG_OVERDOSE("Drug Overdose", DeathCauseCategory.EXTERNAL_AND_INJURY),
    LEAD_POISONING("Lead Poisoning", DeathCauseCategory.EXTERNAL_AND_INJURY),

    // ------------------------------------------------------------------------------------------------
    // MATERNAL_AND_PERINATAL
    // ------------------------------------------------------------------------------------------------
    MATERNAL_DEATHS("Maternal Deaths", DeathCauseCategory.MATERNAL_AND_PERINATAL),
    PERINATAL_COMPLICATIONS("Perinatal Complications", DeathCauseCategory.MATERNAL_AND_PERINATAL),

    // ------------------------------------------------------------------------------------------------
    // CHRONIC_NON_COMMUNICABLE
    // ------------------------------------------------------------------------------------------------
    DIABETES("Diabetes", DeathCauseCategory.CHRONIC_NON_COMMUNICABLE),
    ALCOHOLISM("Alcoholism", DeathCauseCategory.CHRONIC_NON_COMMUNICABLE),
    TOBACCO_RELATED_DISEASE("Tobacco Related Disease", DeathCauseCategory.CHRONIC_NON_COMMUNICABLE),
    STARVATION("Starvation", DeathCauseCategory.CHRONIC_NON_COMMUNICABLE),
    DEHYDRATION("Dehydration", DeathCauseCategory.CHRONIC_NON_COMMUNICABLE),

    // ------------------------------------------------------------------------------------------------
    // HISTORICAL_AND_VAGUE
    // ------------------------------------------------------------------------------------------------
    OLD_AGE("Old Age", DeathCauseCategory.HISTORICAL_AND_VAGUE),
    CONSUMPTION("Consumption (Gluttony)", DeathCauseCategory.HISTORICAL_AND_VAGUE),
    DROPSY("Dropsy", DeathCauseCategory.HISTORICAL_AND_VAGUE),
    ERGOTISM("Ergotism", DeathCauseCategory.HISTORICAL_AND_VAGUE),
    SPANISH_FLU("Spanish Flu", DeathCauseCategory.HISTORICAL_AND_VAGUE),
    ENGLISH_SWEATING_SICKNESS("English Sweating Sickness", DeathCauseCategory.HISTORICAL_AND_VAGUE),
    FEVER("Fever", DeathCauseCategory.HISTORICAL_AND_VAGUE),

    // ------------------------------------------------------------------------------------------------
    // UNKNOWN
    // ------------------------------------------------------------------------------------------------
    UNKNOWN("Unknown Cause", DeathCauseCategory.UNKNOWN),
    OTHER("Other Cause", DeathCauseCategory.UNKNOWN);


    private final String displayName;
    private final DeathCauseCategory deathCauseCategory; // Changed from 'category'

    CauseOfDeath(String displayName, DeathCauseCategory deathCauseCategory) {
        this.displayName = displayName;
        this.deathCauseCategory = deathCauseCategory;
    }

    public static List<CauseOfDeath> getCausesByCategory(DeathCauseCategory category) {
        return Arrays.stream(CauseOfDeath.values())
                .filter(cause -> cause.getDeathCauseCategory() == category)
                .collect(Collectors.toList());
    }
}

