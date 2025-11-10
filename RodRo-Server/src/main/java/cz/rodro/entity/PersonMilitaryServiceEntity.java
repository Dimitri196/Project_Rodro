package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a specific instance of a person's service in a military structure at a certain rank.
 */
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private PersonEntity person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "military_structure_id")
    private MilitaryStructureEntity militaryStructure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "military_rank_id")
    private MilitaryRankEntity militaryRank;

    private Integer enlistmentYear;
    private Integer dischargeYear;

    @Column(length = 1000)
    private String notes;

}
