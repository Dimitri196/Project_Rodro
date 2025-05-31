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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "military_organization_id")
    @JsonBackReference
    private MilitaryOrganizationEntity organization;

    @ManyToOne
    @JoinColumn(name = "army_branch_id")
    private MilitaryArmyBranchEntity armyBranch;

    private String activeFromYear;
    private String activeToYear;
    private String notes;

}
