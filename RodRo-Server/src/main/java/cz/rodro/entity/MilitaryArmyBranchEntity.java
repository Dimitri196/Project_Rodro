package cz.rodro.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing a fundamental branch of military service (e.g., Infantry, Cavalry).
 * It acts as a categorization for military organizations.
 */
@Entity
@Table(name = "military_army_branch")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MilitaryArmyBranchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The official name of the military branch. Must be unique.
     */
    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    /**
     * A description or detailed context regarding the role and history of the branch.
     */
    @Column(length = 1000)
    private String description;

}
