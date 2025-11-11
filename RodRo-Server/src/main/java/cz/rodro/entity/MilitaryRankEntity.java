package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import cz.rodro.constant.MilitaryRankLevel;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "military_rank")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MilitaryRankEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "rankLevel", nullable = false)
    private MilitaryRankLevel rankLevel;

    private String activeFromYear;
    private String activeToYear;
    private String notes;

    /**
     * URL of the rank insignia image.
     */
    private String insigniaImageUrl;

    @OneToMany(mappedBy = "militaryRank", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PersonMilitaryServiceEntity> personMilitaryServices = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "military_organization_id")
    private MilitaryOrganizationEntity militaryOrganization;

    // A new field to link the rank to a specific military structure (e.g., a regiment)
    @ManyToOne
    @JoinColumn(name = "military_structure_id")
    private MilitaryStructureEntity militaryStructure;

}
