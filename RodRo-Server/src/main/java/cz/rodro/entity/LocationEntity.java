package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import cz.rodro.constant.SettlementType;
import cz.rodro.validation.PastOrPresentYear;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a geographical location.
 * <p>
 * A Location may represent a city, town, village, parish, or other settlement type.
 * It can have historical records, associated sources, parishes, persons, cemeteries, and institutions.
 * </p>
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "location",
        uniqueConstraints = @UniqueConstraint(columnNames = "locationName")
)
public class LocationEntity {

    /**
     * Unique identifier of the location.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the location (e.g., "Prague", "Brno").
     */
    @Column(nullable = false)
    @NotNull
    private String locationName;

    /**
     * Year the location was established.
     * Must not be in the future.
     */
    @Column
    @PastOrPresentYear
    private Integer establishmentYear;

    /**
     * Latitude of the location (-90 to 90 degrees).
     */
    @Column
    private Double gpsLatitude;

    /**
     * Longitude of the location (-180 to 180 degrees).
     */
    @Column
    private Double gpsLongitude;

    /**
     * Type of settlement (e.g., VILLAGE, CITY, TOWN).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "settlementType", nullable = false)
    private SettlementType settlementType;

    /**
     * Historical records associated with this location.
     */
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<LocationHistoryEntity> locationHistories;

    /**
     * Sources documenting this location.
     */
    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<SourceEntity> sources;

    /**
     * Persons born at this location.
     */
    @OneToMany(mappedBy = "birthPlace", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<PersonEntity> births;

    /**
     * Persons who died at this location.
     */
    @OneToMany(mappedBy = "deathPlace", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<PersonEntity> deaths;

    /**
     * Parish locations associated with this location.
     */
    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<ParishLocationEntity> parishLocations;

    /**
     * Cemeteries located at this location.
     */
    @OneToMany(mappedBy = "cemeteryLocation", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<CemeteryEntity> cemeteries;

    /**
     * Bidirectional One-to-Many relationship with InstitutionEntity.
     * Mapped by the 'location' field in the InstitutionEntity.
     */
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InstitutionEntity> institutions = new ArrayList<>();

    /**
     * URL of an image representing this location.
     */
    @Column(length = 2000)
    private String locationImageUrl;
}
