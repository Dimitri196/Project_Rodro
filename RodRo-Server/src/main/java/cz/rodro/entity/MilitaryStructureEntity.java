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

    private String unitName;
    private String unitType;

    @ManyToOne
    @JoinColumn(name = "military_organization_id")
    private MilitaryOrganizationEntity organization;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private CountryEntity country;

    private String activeFromYear;
    private String activeToYear;
    private String notes;
}
