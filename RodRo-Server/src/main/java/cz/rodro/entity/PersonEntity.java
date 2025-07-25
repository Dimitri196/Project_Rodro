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

    @Column(nullable = false)
    private String givenName;
    @Column(nullable = false)
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

    @Column(name = "birth_year")
    private Integer birthYear;
    @Column(name = "birth_month")
    private Integer birthMonth;
    @Column(name = "birth_day")
    private Integer birthDay;

    @Column(name = "baptization_year")
    private Integer baptizationYear;
    @Column(name = "baptization_month")
    private Integer baptizationMonth;
    @Column(name = "baptization_day")
    private Integer baptizationDay;

    @Column(name = "death_year")
    private Integer deathYear;
    @Column(name = "death_month")
    private Integer deathMonth;
    @Column(name = "death_day")
    private Integer deathDay;

    @Column(name = "burial_year")
    private Integer burialYear;
    @Column(name = "burial_month")
    private Integer burialMonth;
    @Column(name = "burial_day")
    private Integer burialDay;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PersonOccupationEntity> occupations = new ArrayList<>();

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PersonSourceEvidenceEntity> sourceEvidences = new ArrayList<>();
}
