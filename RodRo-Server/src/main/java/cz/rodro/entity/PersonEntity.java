package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import cz.rodro.constant.CauseOfDeath;
import cz.rodro.constant.Gender;
import cz.rodro.constant.SocialStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a person. This class maps to the "person" table in the database.
 * It contains personal details such as name, identification number, account information,
 * and contact details. It also includes the buyer and seller relationships for invoices.
 */

@Entity(name = "person")
@Getter
@Setter
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String givenName;
    private String givenSurname;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private SocialStatus socialStatus;

    @Enumerated(EnumType.STRING)
    private CauseOfDeath causeOfDeath;

    private String identificationNumber;
    private String note;

    @ManyToOne
    @JsonBackReference("birthPlace")
    private LocationEntity birthPlace;

    @ManyToOne
    @JsonBackReference("deathPlace")
    private LocationEntity deathPlace;

    @ManyToOne
    @JsonBackReference("burialPlace")
    private LocationEntity burialPlace;

    @ManyToOne
    @JsonBackReference("baptizationPlace")
    private LocationEntity baptizationPlace;

    @ManyToOne
    @JsonBackReference("mother")
    private PersonEntity mother;

    @ManyToOne
    @JsonBackReference("father")
    private PersonEntity father;

    @Column(name = "birth_date", nullable = true)
    private LocalDate birthDate;

    @Column(name = "baptization_date", nullable = true)
    private LocalDate baptizationDate;

    @Column(name = "death_date", nullable = true)
    private LocalDate deathDate;

    @Column(name = "burial_date", nullable = true)
    private LocalDate burialDate;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PersonOccupationEntity> occupations = new ArrayList<>();

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PersonSourceEvidenceEntity> sourceEvidences = new ArrayList<>();
}
