package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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

    @ManyToOne
    @JoinColumn(name = "person_id")
    private PersonEntity person;

    @ManyToOne
    @JoinColumn(name = "military_structure_id")
    private MilitaryStructureEntity militaryStructure;

    @ManyToOne
    @JoinColumn(name = "military_rank_id")
    private MilitaryRankEntity militaryRank;

    private String enlistmentYear;
    private String dischargeYear;

    private String notes;

    @OneToMany(mappedBy = "personMilitaryService", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PersonSourceEvidenceEntity> sourceEvidences = new ArrayList<>();
}