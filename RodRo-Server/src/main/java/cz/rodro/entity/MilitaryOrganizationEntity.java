package cz.rodro.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String armyName;    // e.g., "Prussian Army"
    private String armyBranch;  // e.g., "Infantry"

    @ManyToOne
    @JoinColumn(name = "country_id")
    private CountryEntity country;

    private String activeFromYear;
    private String activeToYear;
}
