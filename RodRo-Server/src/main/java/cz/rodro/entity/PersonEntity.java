package cz.rodro.entity;

import cz.rodro.constant.CauseOfDeath;
import cz.rodro.constant.Gender;
import cz.rodro.constant.SocialStatus;
import cz.rodro.entity.helper.AuditableEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import lombok.*;


/**
 * Entity representing a person.
 * Maps to the "person" table in the database.
 * Stores core personal details, partial dates for life events,
 * relationships to parents, occupations, and source evidences.
 */
@Entity(name = "person")
@Table(name = "person",
        indexes = {
                @Index(name = "idx_mother_id", columnList = "mother_id"),
                @Index(name = "idx_father_id", columnList = "father_id"),
                @Index(name = "idx_birth_place_id", columnList = "birth_place_id"),
                @Index(name = "idx_death_place_id", columnList = "death_place_id"),
                @Index(name = "idx_burial_place_id", columnList = "burial_place_id"),
                @Index(name = "idx_baptism_place_id", columnList = "baptism_place_id")
        })
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"mother", "father", "occupations", "sourceEvidences"})
public class PersonEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, length = 100)
    private String givenName;

    @Column(nullable = false, length = 100)
    private String surname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private SocialStatus socialStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private CauseOfDeath causeOfDeath;

    @Column(unique = true, length = 50, nullable = true)
    private String externalId;

    @Column(length = 1000)
    private String bioNote;

    // --- Relationships to LocationEntity ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "birth_place_id")
    private LocationEntity birthPlace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "death_place_id")
    private LocationEntity deathPlace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "burial_place_id")
    private LocationEntity burialPlace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "baptism_place_id")
    private LocationEntity baptismPlace;

    // --- Self-referencing Relationships ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mother_id")
    private PersonEntity mother;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "father_id")
    private PersonEntity father;

    // --- Partial Dates ---
    @Column(name = "birth_year")
    private Integer birthYear;
    @Column(name = "birth_month")
    private Integer birthMonth;
    @Column(name = "birth_day")
    private Integer birthDay;

    @Column(name = "baptism_year")
    private Integer baptismYear;
    @Column(name = "baptism_month")
    private Integer baptismMonth;
    @Column(name = "baptism_day")
    private Integer baptismDay;

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

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PersonOccupationEntity> occupations = new ArrayList<>();


    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PersonSourceEvidenceEntity> sourceEvidences = new ArrayList<>();

}
