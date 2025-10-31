package cz.rodro.service;

import cz.rodro.constant.CauseOfDeath;
import cz.rodro.constant.DeathCauseCategory;
import cz.rodro.dto.StatisticsDTO;
import cz.rodro.entity.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final PersonRepository personRepository;

    @Override
    public List<StatisticsDTO> getAverageLifespanStatistics() {
        List<Object[]> results = personRepository.calculateAverageLifespanByGender();

        return results.stream()
                .map(row -> StatisticsDTO.builder()
                        .statisticType("AverageLifespan")
                        .category(row[0] != null ? row[0].toString() : "Unknown")
                        .value(row[1] != null ? ((Number) row[1]).doubleValue() : 0.0)
                        .unit("Years")
                        .period("All Years")
                        .build()
                )
                .toList();
    }

    /**
     * Aggregates detailed CauseOfDeath counts into the high-level DeathCauseCategory groups.
     */
    @Override
    public List<StatisticsDTO> getCauseOfDeathStatistics() {
        // 1. Fetch detailed counts from the repository (e.g., TUBERCULOSIS: 100, MEASLES: 50)
        List<Object[]> detailedCounts = personRepository.countCauseOfDeathsDetailed(); // ⚠️ Renamed repository method

        // A map to hold the aggregated counts by the high-level category
        Map<DeathCauseCategory, Double> categoryCounts = new HashMap<>();

        for (Object[] row : detailedCounts) {
            // Row[0] is the specific CauseOfDeath enum instance returned by the JPQL query
            CauseOfDeath detailedCause = (CauseOfDeath) row[0];
            Double count = ((Number) row[1]).doubleValue();

            if (detailedCause != null) {
                // 2. Use the Java enum logic to get the high-level category
                DeathCauseCategory category = detailedCause.getDeathCauseCategory();

                // 3. Aggregate the counts
                categoryCounts.merge(category, count, Double::sum);
            }
        }

        // 4. Convert the aggregated map into the final list of StatisticsDTOs
        return categoryCounts.entrySet().stream()
                .map(entry -> StatisticsDTO.builder()
                        .statisticType("CauseOfDeathCategoryCount")
                        // Use the rich display name that includes sub-causes
                        .category(entry.getKey().getDisplayName())
                        .value(entry.getValue())
                        .unit("Count")
                        .period("All Years")
                        .build())
                .sorted(Comparator.comparingDouble(StatisticsDTO::getValue).reversed())
                .toList();
    }

    @Override
    public List<StatisticsDTO> getDeathsByLocationStatistics() {
        return personRepository.countDeathsByLocation().stream()
                .map(row -> StatisticsDTO.builder()
                        .statisticType("DeathCountByLocation")
                        .category(row[0] != null ? row[0].toString() : "Unknown location")
                        .value(((Number) row[1]).doubleValue())
                        .unit("Count")
                        .period("All Years")
                        .build()
                )
                .toList();
    }

    @Override
    public List<StatisticsDTO> getBirthsByYearStatistics() {
        return personRepository.countBirthsByYear().stream()
                .map(row -> StatisticsDTO.builder()
                        .statisticType("BirthCountByYear")
                        .category(row[0].toString()) // year
                        .value(((Number) row[1]).doubleValue())
                        .unit("Count")
                        .period(row[0].toString())
                        .build()
                )
                .toList();
    }

    @Override
    public List<StatisticsDTO> getDeathsByYearStatistics() {
        return personRepository.countDeathsByYear().stream()
                .map(row -> StatisticsDTO.builder()
                        .statisticType("DeathCountByYear")
                        .category(row[0].toString()) // year
                        .value(((Number) row[1]).doubleValue())
                        .unit("Count")
                        .period(row[0].toString())
                        .build()
                )
                .toList();
    }

    @Override
    public List<StatisticsDTO> getBirthsByLocationStatistics() {
        return personRepository.countBirthsByLocation().stream()
                .map(row -> StatisticsDTO.builder()
                        .statisticType("BirthCountByLocation")
                        .category(row[0] != null ? row[0].toString() : "Unknown location")
                        .value(((Number) row[1]).doubleValue())
                        .unit("Count")
                        .period("All Years")
                        .build()
                )
                .toList();
    }

    @Override
    public List<StatisticsDTO> getPeopleBySocialStatusStatistics() {
        return personRepository.countPeopleBySocialStatus().stream()
                .map(row -> StatisticsDTO.builder()
                        .statisticType("PeopleBySocialStatus")
                        .category(row[0] != null ? row[0].toString() : "Unknown")
                        .value(((Number) row[1]).doubleValue())
                        .unit("Count")
                        .period("All Years")
                        .build()
                )
                .toList();
    }

    @Override
    public List<StatisticsDTO> getCauseOfDeathByGenderStatistics() {
        // NOTE: This currently still uses the detailed CauseOfDeath.
        // If you want to group this by DeathCauseCategory, you'll need to update the repository query
        // and implement a similar aggregation logic here.
        return personRepository.countCauseOfDeathByGender().stream()
                .map(row -> StatisticsDTO.builder()
                        .statisticType("CauseOfDeathByGender")
                        .category(row[0] != null ? row[0].toString() : "Unknown")
                        .subCategory(row[1] != null ? row[1].toString() : "Unknown")
                        .value(((Number) row[2]).doubleValue())
                        .unit("Count")
                        .period("All Years")
                        .build()
                )
                .toList();
    }

    @Override
    public List<StatisticsDTO> getDeathsByAgeGroupStatistics() {
        return personRepository.countDeathsByAgeGroup().stream()
                .map(row -> StatisticsDTO.builder()
                        .statisticType("DeathsByAgeGroup")
                        .category(row[0].toString()) // age range label
                        .value(((Number) row[1]).doubleValue())
                        .unit("Count")
                        .period("All Years")
                        .build()
                )
                .toList();
    }

    @Override
    public List<StatisticsDTO> getDeathsBySettlementTypeStatistics() {
        return personRepository.countDeathsBySettlementType().stream()
                .map(row -> StatisticsDTO.builder()
                        .statisticType("DeathsBySettlementType")
                        .category(row[0] != null ? row[0].toString() : "Unknown Settlement")
                        .value(((Number) row[1]).doubleValue())
                        .unit("Count")
                        .period("All Years")
                        .build()
                )
                .toList();
    }

    // --- SCIENTIFIC MORTALITY IMPLEMENTATIONS ---

    @Override
    public List<StatisticsDTO> getCrudeDeathRateStatistics() {
        // Reusing countDeathsByYear to represent the trend
        return personRepository.countDeathsByYear().stream()
                .map(row -> StatisticsDTO.builder()
                        .statisticType("CrudeDeathRateProxy")
                        .category(row[0].toString()) // deathYear
                        .value(((Number) row[1]).doubleValue())
                        .unit("Deaths")
                        .period(row[0].toString())
                        .build()
                )
                .toList();
    }

    @Override
    public List<StatisticsDTO> getInfantMortalityRateStatistics() {
        // 1. Get total births by year (Denominator: required for IMR)
        Map<Integer, Double> birthMap = getBirthsByYearStatistics().stream()
                .collect(Collectors.toMap(
                        dto -> Integer.parseInt(dto.getCategory()),
                        StatisticsDTO::getValue
                ));

        // 2. Get infant deaths by year (Numerator)
        List<Object[]> infantDeaths = personRepository.countInfantDeathsByYear();

        // 3. Calculate IMR per 1,000 live births
        return infantDeaths.stream()
                .map(row -> {
                    Integer year = (Integer) row[0];
                    double infantDeathCount = ((Number) row[1]).doubleValue();
                    double totalBirthCount = birthMap.getOrDefault(year, 0.0);

                    double imr = 0.0;
                    if (totalBirthCount > 0) {
                        // IMR = (Infant Deaths / Total Births) * 1000
                        imr = (infantDeathCount / totalBirthCount) * 1000.0;
                    }

                    return StatisticsDTO.builder()
                            .statisticType("InfantMortalityRate")
                            .category(year.toString())
                            .value(imr)
                            .unit("Per 1,000 Births")
                            .period(year.toString())
                            .build();
                })
                .filter(dto -> dto.getValue() > 0) // Only include years where IMR was calculable
                .toList();
    }

    @Override
    public List<StatisticsDTO> getDeathsByAllDimensionsStatistics() {
        // This query provides data grouped by [Location Name, Death Year, Age Group, Count]
        return personRepository.countDeathsByAllDimensions().stream()
                .map(row -> StatisticsDTO.builder()
                        .statisticType("MultiDimensionalMortality")
                        .category(row[0] != null ? row[0].toString() : "Unknown Location") // Location Name
                        .period(row[1] != null ? row[1].toString() : "Unknown Year")     // Death Year
                        .subCategory(row[2] != null ? row[2].toString() : "Unknown Age") // Age Group Label
                        .value(((Number) row[3]).doubleValue())                          // Death Count
                        .unit("Count")
                        .build()
                )
                .toList();
    }

    /**
     * Helper method to map raw query results (Object[]) to StatisticsDTO.
     * The Object[] contains [ageGroup (String), deathCount (Long)].
     */
    private StatisticsDTO mapToObjectArrayToDTO(Object[] row) {
        // Ensure the array has the expected structure and types
        if (row.length < 2 || !(row[0] instanceof String) || !(row[1] instanceof Number)) {
            throw new IllegalStateException("Unexpected row structure from age group query.");
        }

        String category = (String) row[0]; // The Age Group

        // The count is returned as a Long/BigInteger by the repository,
        // convert it to Double to match the DTO's 'value' field type.
        Double value = ((Number) row[1]).doubleValue();

        // FIX: Use the Lombok @Builder pattern to set only 'category' and 'value'.
        return StatisticsDTO.builder()
                .category(category)
                .value(value)
                .statisticType("DeathsByAgeGroup") // Set a default type
                .unit("Count")                     // Set a default unit
                .build();
    }

    /**
     * Retrieves the count of deaths grouped by age category for a specific location
     * and maps the raw List<Object[]> result to List<StatisticsDTO>.
     * * @param locationName The name of the location to filter the data by.
     * @return A list of StatisticsDTO.
     */
    @Override
    public List<StatisticsDTO> countDeathsByAgeGroupAndLocation(String locationName) {
        if (locationName == null || locationName.trim().isEmpty()) {
            throw new IllegalArgumentException("Location name must not be empty for this filtered query.");
        }

        // 1. Execute the query, which returns List<Object[]>
        List<Object[]> rawData = personRepository.countDeathsByAgeGroupAndLocation(locationName);

        // 2. Map the List<Object[]> to the required List<StatisticsDTO>
        return rawData.stream()
                .map(this::mapToObjectArrayToDTO)
                .collect(Collectors.toList());
    }

}
