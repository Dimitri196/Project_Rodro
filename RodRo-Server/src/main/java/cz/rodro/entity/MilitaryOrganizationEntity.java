package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "military_organization")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MilitaryOrganizationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String armyName;

    @ManyToOne
    @JoinColumn(name = "army_branch_id")
    private MilitaryArmyBranchEntity armyBranch;

    @ManyToOne
    @JoinColumn(name = "country_id")
    @JsonBackReference
    private CountryEntity country;

    private String activeFromYear;
    private String activeToYear;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MilitaryStructureEntity> structures = new ArrayList<>();

    // You might also have a OneToMany back to MilitaryRankEntity
    @OneToMany(mappedBy = "militaryOrganization", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MilitaryRankEntity> militaryRanks = new ArrayList<>();

    private String organizationImageUrl;

    private String organizationDescription;


}
