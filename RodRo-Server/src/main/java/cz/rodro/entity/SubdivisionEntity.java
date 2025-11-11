package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Entity representing a geographical or administrative subdivision (e.g., a county, region, or sub-district).
 * <p>
 * This entity links to a parent {@link DistrictEntity} and identifies its {@link LocationEntity}
 * that serves as the administrative center. It uses String fields for temporal data to accommodate
 * fuzzy, non-standard, or contextual historical dates (e.g., "circa 1850", "Before 1400 AD", "200 BC").
 * </p>
 */
@Entity
@Table(name = "subdivision")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubdivisionEntity {

    /**
     * Unique identifier for the Subdivision.
     * <p>
     * This field is the primary key and is automatically generated upon persistence.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The official name of the subdivision.
     * <p>
     * This field is mandatory and cannot be null.
     * </p>
     */
    @Column(nullable = false)
    private String name;

    /**
     * The parent {@link DistrictEntity} to which this subdivision belongs.
     * <p>
     * This establishes a mandatory Many-to-One relationship, lazily fetched for efficiency.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    private DistrictEntity district;

    /**
     * The {@link LocationEntity} (e.g., a city or town) designated as the administrative center of this subdivision.
     * <p>
     * This establishes a mandatory Many-to-One relationship, lazily fetched.
     * {@code @JsonManagedReference} is used to handle the forward part of the JSON relationship.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "administrative_center_id", nullable = false)
    @JsonManagedReference
    private LocationEntity administrativeCenter;

    /**
     * The year or period when the subdivision was officially established.
     * <p>
     * **Stored as a String** to accommodate inexact or contextual historical dates like "circa 1850" or "Before 1400 AD".
     * </p>
     */
    @Column(name = "establishment_year")
    private String subdivisionEstablishmentYear;

    /**
     * The year or period when the subdivision was officially cancelled, merged, or dissolved.
     * <p>
     * **Stored as a String** to accommodate inexact or contextual historical dates.
     * </p>
     */
    @Column(name = "cancellation_year")
    private String subdivisionCancellationYear;

    /**
     * A list of {@link LocationHistoryEntity} records that relate to changes within this subdivision.
     * <p>
     * This establishes a One-to-Many relationship, mapped by the 'subdivision' field in the history entity.
     * </p>
     */
    @OneToMany(mappedBy = "subdivision")
    private List<LocationHistoryEntity> historyEntries;
}
