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

/**
 * Entity representing a specific Military Organization (e.g., Army, Corps, Division).
 * It links branches, countries, structures, and ranks together.
 */
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

    /**
     * The official name of the organization.
     */
    @Column(nullable = false, length = 255)
    private String name;

    /**
     * The primary branch of the organization.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "army_branch_id", nullable = false)
    private MilitaryArmyBranchEntity armyBranch;

    /**
     * The country this organization belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    @JsonBackReference
    private CountryEntity country;

    /**
     * The year the organization became active (stored as a String for flexibility with BC/approximate dates).
     */
    private String activeFromYear;

    /**
     * The year the organization became inactive.
     */
    private String activeToYear;

    /**
     * The subordinate structures (e.g., Brigades, Regiments) that report to this organization.
     */
    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Handles bidirectional relationship with MilitaryStructureEntity
    private List<MilitaryStructureEntity> structures = new ArrayList<>();

    /**
     * The ranks specifically used or associated with this military organization.
     */
    @OneToMany(mappedBy = "militaryOrganization", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MilitaryRankEntity> militaryRanks = new ArrayList<>();

    /**
     * A short summary or contextual note regarding the organization's history.
     */
    @Column(length = 500)
    private String historyContext;

    /**
     * The full, detailed historical description of the organization.
     */
    @Column(length = 4000)
    private String description;

    /**
     * URL linking to the organization's image (flag, crest, etc.).
     */
    private String imageUrl;

}
