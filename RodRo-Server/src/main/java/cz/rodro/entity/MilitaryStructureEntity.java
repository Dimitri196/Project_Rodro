package cz.rodro.entity;

import jakarta.persistence.*;
import lombok.*;

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

    private String armyName;        // e.g. "Prussian Army"
    private String armyBranch;      // e.g. "Infantry", "Cavalry"
    private String unitName;        // e.g. "1st Regiment"
    private String unitType;        // e.g. "Regiment", "Company"

    @ManyToOne
    @JoinColumn(name = "country_id")
    private CountryEntity country;

    private String countryName;     // optional redundancy

    private String activeFromYear;
    private String activeToYear;

    private String notes;

}
