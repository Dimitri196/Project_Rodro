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
    @JoinColumn(name = "military_organization_id")
    private MilitaryOrganizationEntity organization;

    private String activeFromYear;
    private String activeToYear;

    private String notes;
}
