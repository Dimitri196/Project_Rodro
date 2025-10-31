package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import cz.rodro.constant.AttributionType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "source_attribution")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SourceAttributionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AttributionType type; // e.g. BIRTH, DEATH, OCCUPATION, MILITARY, FAMILY

    @Lob
    private String note;

    // --- Linked entities ---

    @ManyToOne
    @JsonBackReference("person-attribution")
    @JoinColumn(name = "person_id")
    private PersonEntity person;

    @ManyToOne
    @JoinColumn(name = "occupation_id")
    private PersonOccupationEntity occupation;

    @ManyToOne
    @JsonBackReference("family-attribution")
    @JoinColumn(name = "family_id")
    private FamilyEntity family;

    @ManyToOne
    @JsonBackReference("military-attribution")
    @JoinColumn(name = "military_service_id")
    private PersonMilitaryServiceEntity militaryService;

    // --- Source link ---
    @ManyToOne(optional = false)
    @JsonBackReference("source-attribution")
    @JoinColumn(name = "source_id", nullable = false)
    private SourceEntity source;
}
