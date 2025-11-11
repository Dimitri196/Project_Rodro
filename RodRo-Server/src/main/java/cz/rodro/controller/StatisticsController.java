package cz.rodro.controller;

import cz.rodro.dto.StatisticsDTO;
import cz.rodro.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/average-lifespan")
    public List<StatisticsDTO> getAverageLifespan() {
        return statisticsService.getAverageLifespanStatistics();
    }

    /**
     * Retrieves death counts by age group.
     * If the 'location' parameter is provided, the results are filtered for that specific location.
     */
    @GetMapping("/deaths-by-age-group")
    public List<StatisticsDTO> getDeathsByAgeGroup(

            @RequestParam(required = false) String location
    ) {
        if (location != null && !location.trim().isEmpty()) {
            return statisticsService.countDeathsByAgeGroupAndLocation(location);
        }

        return statisticsService.getDeathsByAgeGroupStatistics();
    }

    @GetMapping("/births-by-year")
    public List<StatisticsDTO> getBirthsByYear() {
        return statisticsService.getBirthsByYearStatistics();
    }

    @GetMapping("/deaths-by-year")
    public List<StatisticsDTO> getDeathsByYear() {
        return statisticsService.getDeathsByYearStatistics();
    }

    /**
     * Retrieves death counts aggregated by the high-level DeathCauseCategory.
     * This uses the refactored Service Layer logic.
     */
    @GetMapping("/cause-of-death-category")
    public List<StatisticsDTO> getCauseOfDeathCategory() {
        return statisticsService.getCauseOfDeathStatistics();
    }

    /**
     * Retrieves death counts by the specific CauseOfDeath, further broken down by Gender.
     * Note: This endpoint still uses the detailed CauseOfDeath (not the category).
     */
    @GetMapping("/cause-of-death-by-gender")
    public List<StatisticsDTO> getCauseOfDeathByGender() {
        return statisticsService.getCauseOfDeathByGenderStatistics();
    }

    @GetMapping("/deaths-by-location")
    public List<StatisticsDTO> getDeathsByLocation() {
        return statisticsService.getDeathsByLocationStatistics();
    }

    @GetMapping("/births-by-location")
    public List<StatisticsDTO> getBirthsByLocation() {
        return statisticsService.getBirthsByLocationStatistics();
    }

    @GetMapping("/people-by-social-status")
    public List<StatisticsDTO> getPeopleBySocialStatus() {
        return statisticsService.getPeopleBySocialStatusStatistics();
    }

    @GetMapping("/deaths-by-settlement-type")
    public List<StatisticsDTO> getDeathsBySettlementType() {
        return statisticsService.getDeathsBySettlementTypeStatistics();
    }

    @GetMapping("/crude-death-rate")
    public List<StatisticsDTO> getCrudeDeathRate() {
        return statisticsService.getCrudeDeathRateStatistics();
    }

    @GetMapping("/infant-mortality-rate")
    public List<StatisticsDTO> getInfantMortalityRate() {
        return statisticsService.getInfantMortalityRateStatistics();
    }

    @GetMapping("/deaths-by-all-dimensions")
    public List<StatisticsDTO> getDeathsByAllDimensions() {
        return statisticsService.getDeathsByAllDimensionsStatistics();
    }
}
