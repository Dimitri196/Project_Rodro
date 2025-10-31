package cz.rodro.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for carrying generic statistical results.
 * Designed to be flexible enough for Cause of Death, Life Expectancy, Location-based counts, etc.
 */
@Data // Generates Getters, Setters, toString, equals, and hashCode
@NoArgsConstructor // Generates a constructor with no parameters
@AllArgsConstructor // Generates a constructor with all parameters
@Builder // Generates a Builder pattern for easy object creation
public class StatisticsDTO {

    /**
     * Identifies the type of statistic.
     * Examples: "CauseOfDeathCount", "AverageLifespan", "DeathRateByLocation"
     */
    private String statisticType;

    /**
     * The main numeric result (count, average, percentage, rate).
     */
    private Double value;

    /**
     * The unit of the value.
     * Examples: "Count", "Years", "Percentage", "Age"
     */
    private String unit;

    /**
     * The primary categorical breakdown, often the item being counted or averaged.
     * Examples: "Heart Disease" (for Cause of Death), "California" (for location).
     */
    private String category;

    /**
     * An optional secondary categorical breakdown, often used for filtering or grouping.
     * Examples: "Male", "Female", "Urban", "Rural"
     */
    private String subCategory;

    /**
     * The period (year, decade, or range) the statistic applies to.
     * Examples: "2023", "2000-2010"
     */
    private String period;

}
