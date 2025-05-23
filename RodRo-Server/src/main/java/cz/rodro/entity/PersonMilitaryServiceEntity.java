package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "person_military_service")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonMilitaryServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id")
    private PersonEntity person;

    @ManyToOne
    @JoinColumn(name = "military_structure_id")
    private MilitaryStructureEntity structure;

    @ManyToOne
    @JoinColumn(name = "military_occupation_id")
    private MilitaryOccupationEntity militaryOccupation;

    private String enlistmentYear;
    private String dischargeYear;

    private String notes;

    @OneToMany(mappedBy = "militaryService", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("military-service-evidence")
    private List<MilitaryServiceSourceEvidenceEntity> sourceEvidences;
}