package cz.rodro.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

/**
 * Represents the link between a {@link ParishEntity} and a {@link LocationEntity}.
 * <p>
 * Contains optional denormalized fields {@link #parishName} and {@link #locationName} for convenience in display.
 * Typically, the canonical values are stored in the related Parish and Location entities.
 * </p>
 */
@Entity
@Table(name = "parish_location")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParishLocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parish_id", nullable = false)
    private ParishEntity parish;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private LocationEntity location;

    private String parishName;
    private String locationName;
    private String mainChurchName;

    /** Marks if this location is the main location for the parish */
    @Column(name = "main_location", nullable = false, columnDefinition = "TINYINT(1)")
    private boolean mainLocation;
}
