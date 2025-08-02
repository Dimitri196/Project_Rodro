package cz.rodro.entity;

import cz.rodro.constant.CauseOfDeath;
import cz.rodro.constant.Gender;
import cz.rodro.constant.SocialStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a person. This class maps to the "person" table in the database.
 * It contains core personal details such as name, identification, gender, social status,
 * cause of death, and details about their birth, baptization, death, and burial.
 * It also models relationships to parents and lists of occupations and source evidences.
 */
@Entity(name = "person") // Explicit name is good practice
@Table(name = "person") // Explicit @Table annotation is also good practice
@Getter
@Setter
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100) // Added length for practical string columns
    private String givenName;

    @Column(nullable = false, length = 100)
    private String givenSurname;

    @Enumerated(EnumType.STRING) // Stores enum name as string in DB
    @Column(nullable = true) // Assuming gender can be optional
    private Gender gender; // Assuming Gender is an enum

    @Enumerated(EnumType.STRING)
    @Column(nullable = true) // Assuming social status can be optional
    private SocialStatus socialStatus; // Assuming SocialStatus is an enum

    @Enumerated(EnumType.STRING)
    @Column(nullable = true) // Assuming cause of death can be optional
    private CauseOfDeath causeOfDeath; // Assuming CauseOfDeath is an enum

    @Column(unique = true, length = 50) // Added unique and length for identification number
    private String identificationNumber;

    @Column(length = 1000) // Added length for notes
    private String note;

    // --- Relationships to LocationEntity (ManyToOne) ---
    @ManyToOne(fetch = FetchType.LAZY) // Use LAZY fetching for performance unless always needed
    @JoinColumn(name = "birth_place_id") // Explicit join column name is good practice
    // Removed @JsonBackReference: JSON serialization is handled by DTOs
    private LocationEntity birthPlace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "death_place_id")
    // Removed @JsonBackReference
    private LocationEntity deathPlace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "burial_place_id")
    // Removed @JsonBackReference
    private LocationEntity burialPlace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "baptization_place_id")
    // Removed @JsonBackReference
    private LocationEntity baptizationPlace;

    // --- Self-referencing Relationships (ManyToOne) ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mother_id") // Explicit join column name
    // Removed @JsonBackReference
    private PersonEntity mother;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "father_id") // Explicit join column name
    // Removed @JsonBackReference
    private PersonEntity father;

    // --- Date Fields (Nullable Integers for potentially incomplete dates) ---
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

    // --- One-to-Many Relationships (Collections) ---
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) // LAZY fetch
    // Removed @JsonManagedReference: JSON serialization is handled by DTOs
    private List<PersonOccupationEntity> occupations = new ArrayList<>();

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) // LAZY fetch
    // Removed @JsonManagedReference
    private List<PersonSourceEvidenceEntity> sourceEvidences = new ArrayList<>();

}
