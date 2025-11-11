package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

/**
 * JPA Entity representing a historical (temporal) association between a Country and a Continent.
 * This entity allows tracking of transcontinental countries and historical changes in geographical definition.
 */
@Entity
@Table(name = "country_continent_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountryContinentHistoryEntity {

    /** The primary key for the history record. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The Country associated with this geographical history record. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    @JsonBackReference // The 'back' side of the Country -> History relation
    private CountryEntity country;

    /** The Continent associated with this geographical history record. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "continent_id", nullable = false)
    @JsonBackReference
    private ContinentEntity continent;

    /** The year this geographical association began (inclusive). */
    @Column(nullable = false, length = 10)
    private String startYear;

    /** The year this geographical association ended (exclusive). Null if the association is still current. */
    @Column(length = 10)
    private String endYear;

}
