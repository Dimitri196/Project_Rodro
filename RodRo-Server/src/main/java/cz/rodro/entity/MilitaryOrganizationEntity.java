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
    private String armyBranch;

    @ManyToOne
    @JoinColumn(name = "country_id")
    @JsonBackReference
    private CountryEntity country;

    private String activeFromYear;
    private String activeToYear;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MilitaryStructureEntity> structures = new ArrayList<>();
}
