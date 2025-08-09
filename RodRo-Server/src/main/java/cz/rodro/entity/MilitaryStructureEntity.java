package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "military_structure")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MilitaryStructureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String unitName;
    private String unitType;
    private String activeFromYear;
    private String activeToYear;
    private String notes; // Added notes field

    @Column(nullable = true)
    private String bannerImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "military_organization_id")
    @JsonBackReference("organization-structures")
    private MilitaryOrganizationEntity organization;

    // New self-referential relationship for parent structure
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_structure_id")
    @JsonBackReference("parent-substructures")
    private MilitaryStructureEntity parentStructure;

    // New self-referential relationship for sub-structures
    @OneToMany(mappedBy = "parentStructure", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("parent-substructures")
    private List<MilitaryStructureEntity> subStructures = new ArrayList<>();
}
