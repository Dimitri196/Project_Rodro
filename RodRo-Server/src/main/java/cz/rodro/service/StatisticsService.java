package cz.rodro.service;

import cz.rodro.dto.StatisticsDTO;
import java.util.List;

public interface StatisticsService {

    List<StatisticsDTO> getAverageLifespanStatistics();
    List<StatisticsDTO> getCauseOfDeathStatistics();
    List<StatisticsDTO> getDeathsByLocationStatistics();
    List<StatisticsDTO> getBirthsByYearStatistics();

    /**
     * Calculates the death counts grouped by the specific year of death.
     * Useful for time-series analysis and mortality trends.
     * @return List of StatisticsDTO objects representing death counts per year.
     */
    List<StatisticsDTO> getDeathsByYearStatistics();

    /**
     * Calculates the birth counts grouped by the birth location (LocationEntity name).
     * @return List of StatisticsDTO objects representing birth counts per location.
     */
    List<StatisticsDTO> getBirthsByLocationStatistics();

    /**
     * Calculates the distribution of individuals grouped by their Social Status.
     * @return List of StatisticsDTO objects representing the count of people by social status.
     */
    List<StatisticsDTO> getPeopleBySocialStatusStatistics();

    /**
     * Calculates the counts of persons grouped by Cause of Death AND Gender.
     * Provides a gender-specific breakdown of mortality causes.
     * @return List of StatisticsDTO objects with cause counts broken down by gender.
     */
    List<StatisticsDTO> getCauseOfDeathByGenderStatistics();

    /**
     * Calculates the distribution of deaths across different age groups (e.g., 0-10, 11-20).
     * @return List of StatisticsDTO objects representing death counts per age group.
     */
    List<StatisticsDTO> getDeathsByAgeGroupStatistics();

    /**
     * Calculates the number of deaths grouped by the death place's SettlementType.
     * @return List of StatisticsDTO objects representing death counts per settlement type.
     */
    List<StatisticsDTO> getDeathsBySettlementTypeStatistics(); // <-- Added implementation

    /**
     * Endpoint for calculating the Crude Death Rate trend (represented by raw Deaths per year).
     * @return List of StatisticsDTO objects representing the death count trend.
     */
    List<StatisticsDTO> getCrudeDeathRateStatistics();

    /**
     * Calculates the Infant Mortality Rate (IMR) per 1,000 live births over time.
     * Requires total births and infant deaths by year.
     * @return List of StatisticsDTO objects representing IMR per year.
     */
    List<StatisticsDTO> getInfantMortalityRateStatistics();

    /**
     * Retrieves comprehensive death data grouped by Location, Year, and Age Group.
     * Used for generating detailed Mortality Tables and Age-Specific Death Histograms.
     * @return List of StatisticsDTO objects with multi-dimensional death statistics.
     */
    List<StatisticsDTO> getDeathsByAllDimensionsStatistics();

    List<StatisticsDTO> countDeathsByAgeGroupAndLocation(String locationName);
}
