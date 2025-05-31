package cz.rodro.entity;

import jakarta.persistence.*;
import lombok.*;

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

    private String rankName;
    private String rankDescription;
    private String rankLevel;

    @ManyToOne
    @JoinColumn(name = "army_branch_id")
    private MilitaryArmyBranchEntity armyBranch;

    private String activeFromYear;
    private String activeToYear;

    private String notes;


}
