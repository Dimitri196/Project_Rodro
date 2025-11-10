package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import cz.rodro.constant.InstitutionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a historical or current institution (e.g., an academic body,
 * a ruling council, or a government office) within the geographical context
 * of a country and specific location.
 */
@Entity
@Table(name = "institution")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionEntity {

    /**
     * Unique identifier for the institution.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The official name of the institution. Cannot be blank.
     */
    @Column(nullable = false)
    @NotBlank(message = "Institution name is required")
    private String name;

    /**
     * A detailed description of the institution's purpose, history, or role.
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * The country to which this institution belongs (Foreign Key relationship).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private CountryEntity country;

    /**
     * The year or era the institution was established.
     * Stored as a String to support BC/BCE dates and approximate values (e.g., "300 BC", "c. 1000").
     */
    @Column(nullable = false)
    private String establishmentYear;

    /**
     * The year or era the institution was dissolved or ceased to exist. Null if currently active.
     * Stored as a String to support BC/BCE dates and approximate values (e.g., "50 BCE", "c. 1450").
     */
    private String cancellationYear;

    /**
     * The specific geographical location (city, province, etc.) associated with the institution.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    @JsonManagedReference
    private LocationEntity location;

    /**
     * A list of occupations or positions associated with this institution.
     * Managed by the 'institution' field in the OccupationEntity.
     */
    @OneToMany(mappedBy = "institution", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OccupationEntity> occupations = new ArrayList<>();

    /**
     * URL for the image of the institution's official seal or emblem.
     */
    @Column(columnDefinition = "TEXT")
    private String sealImageUrl;

    /**
     * The categorical type of the institution (e.g., ROYAL, MILITARY, ACADEMIC).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InstitutionType type;
}
