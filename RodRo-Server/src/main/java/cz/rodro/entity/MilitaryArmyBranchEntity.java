package cz.rodro.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "army_branch_name", nullable = false, unique = true)
    private String armyBranchName; // e.g. "Infantry", "Artillery"

    // In MilitaryArmyBranchEntity.java
    @OneToMany(mappedBy = "armyBranch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MilitaryRankEntity> ranks = new ArrayList<>();
}
