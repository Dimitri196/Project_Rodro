package cz.rodro.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "military_occupation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MilitaryOccupationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rankName;       // e.g. "Lieutenant", "Rotmistrz"
    private String rankCode;       // Optional NATO/structure-specific code
    private String description;
    private String notes;

    @ManyToOne
    @JoinColumn(name = "structure_id")
    private MilitaryStructureEntity structure; // The unit structure where this rank applied
}
