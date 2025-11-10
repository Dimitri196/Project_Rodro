package cz.rodro.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object (DTO) for Country information.
 * This object is used to pass validated country data between the web/controller layer
 * and the service layer. It follows the API contract and includes JSR-380 validation constraints.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryDTO {

    /**
     * The unique identifier for the Country record.
     * Mapped to '_id' for standard frontend integration.
     */
    @JsonProperty("_id")
    private Long id;

    /**
     * The official, required name of the country in the Polish language.
     * @apiNote Must be between 2 and 100 characters.
     */
    @NotNull(message = "The country name in Polish is required.")
    @Size(min = 2, max = 100, message = "Polish name must be between 2 and 100 characters.")
    private String nameInPolish;

    /**
     * The official, required name of the country in the English language.
     * @apiNote Must be between 2 and 100 characters.
     */
    @NotNull(message = "The country name in English is required.")
    @Size(min = 2, max = 100, message = "English name must be between 2 and 100 characters.")
    private String nameInEnglish;

    /**
     * The year the country was established (e.g., "1918").
     * Stored as a String to accommodate non-standard historical periods.
     */
    private String establishmentYear;

    /**
     * The year the country was dissolved or cancelled (e.g., "1939").
     * Stored as a String to accommodate non-standard historical periods.
     */
    private String cancellationYear;

    /**
     * A list of {@code ProvinceDTO}s belonging to this country.
     * This field is typically ignored during serialization to prevent JSON recursion
     * unless a deep fetch is explicitly required by an endpoint.
     */
    private List<ProvinceDTO> provinces;

    /**
     * The URL pointing to the image file for the country's flag, if available.
     */
    private String flagImgUrl;

    /**
     * A list of {@code CountryContinentHistoryDTO}s showing the temporal association
     * between this country and continents.
     */
    // ** REMOVE @JsonIgnore **
    private List<CountryContinentHistoryDTO> continentHistory;

}
