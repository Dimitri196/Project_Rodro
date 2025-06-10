package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import cz.rodro.constant.MaritalStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "family")
@Getter
@Setter
public class FamilyEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "marriage_date", nullable = false)
        private LocalDate marriageDate;

        @ManyToOne
        @JoinColumn(name = "marriage_location_id", referencedColumnName = "id", nullable = false)
        private LocationEntity marriageLocation;

        @ManyToOne
        @JoinColumn(name = "spouse_male_id", referencedColumnName = "id", nullable = false)
        private PersonEntity spouseMale;

        @Enumerated(EnumType.STRING)
        @Column(name = "marital_status_spouse_male", nullable = false)
        private MaritalStatus maritalStatusForSpouseMale;

        @ManyToOne
        @JoinColumn(name = "spouse_female_id", referencedColumnName = "id", nullable = false)
        private PersonEntity spouseFemale;

        @Enumerated(EnumType.STRING)
        @Column(name = "marital_status_spouse_female", nullable = false)
        private MaritalStatus maritalStatusForSpouseFemale;

        @ManyToOne
        @JsonBackReference
        private PersonEntity witnessesMaleSide1;

        @ManyToOne
        @JsonBackReference
        private PersonEntity witnessesMaleSide2;

        @ManyToOne
        @JsonBackReference
        private PersonEntity witnessesFemaleSide1;

        @ManyToOne
        @JsonBackReference
        private PersonEntity witnessesFemaleSide2;

        @Column(name = "source")
        private String source;

        @Column(name = "note", length = 500)
        private String note;

        @OneToMany
        @JoinTable(
                name = "family_children",
                joinColumns = @JoinColumn(name = "family_id"),
                inverseJoinColumns = @JoinColumn(name = "child_id")
        )
        private List<PersonEntity> children = new ArrayList<>();
}
