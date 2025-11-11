package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import cz.rodro.constant.AttributionType;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing the attribution link between a specific {@link SourceEntity}
 * and various entities (Person, Family, Occupation, Military Service) documented within that source.
 * <p>
 * This entity serves as a flexible join table to record exactly what data points
 * within the source are relevant to which biographical or relationship entities,
 * often including a specific context type and notes.
 * </p>
 */
@Entity
@Table(name = "source_attribution")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SourceAttributionEntity {

    /**
     * Unique identifier for the Source Attribution record.
     * <p>
     * This field is the primary key and is automatically generated upon persistence.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The type of attribution or evidence being recorded (e.g., BIRTH, DEATH, MARRIAGE).
     * <p>
     * Mapped as an enumerated String.
     * </p>
     */
    @Enumerated(EnumType.STRING)
    private AttributionType type;

    /**
     * Detailed notes providing context for the attribution within the source.
     * <p>
     * Mapped as a large object (LOB) to accommodate extensive text.
     * </p>
     */
    @Lob
    private String note;

    /**
     * The {@link PersonEntity} to which this source segment is attributed.
     * <p>
     * This is an optional Many-to-One relationship. {@code @JsonBackReference} prevents
     * infinite recursion during JSON serialization.
     * </p>
     */
    @ManyToOne
    @JsonBackReference("person-attribution")
    @JoinColumn(name = "person_id")
    private PersonEntity person;

    /**
     * The {@link PersonOccupationEntity} record that this source segment provides evidence for.
     * <p>
     * This is an optional Many-to-One relationship.
     * </p>
     */
    @ManyToOne
    @JoinColumn(name = "occupation_id")
    private PersonOccupationEntity occupation;

    /**
     * The {@link FamilyEntity} (marriage/relationship) to which this source segment is attributed.
     * <p>
     * This is an optional Many-to-One relationship. {@code @JsonBackReference} prevents
     * infinite recursion during JSON serialization.
     * </p>
     */
    @ManyToOne
    @JsonBackReference("family-attribution")
    @JoinColumn(name = "family_id")
    private FamilyEntity family;

    /**
     * The {@link PersonMilitaryServiceEntity} record that this source segment provides evidence for.
     * <p>
     * This is an optional Many-to-One relationship. {@code @JsonBackReference} prevents
     * infinite recursion during JSON serialization.
     * </p>
     */
    @ManyToOne
    @JsonBackReference("military-attribution")
    @JoinColumn(name = "military_service_id")
    private PersonMilitaryServiceEntity militaryService;

    /**
     * The {@link SourceEntity} to which this attribution belongs.
     * <p>
     * This is a mandatory Many-to-One relationship. {@code @JsonBackReference} prevents
     * infinite recursion during JSON serialization.
     * </p>
     */
    @ManyToOne(optional = false)
    @JsonBackReference("source-attribution")
    @JoinColumn(name = "source_id", nullable = false)
    private SourceEntity source;
}

