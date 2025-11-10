package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * JPA Entity representing a historical or contemporary Country in the application's database.
 * This entity serves as the primary root for geographical and organizational data,
 * including provinces and military organizations.
 */
@Entity
@Table(name = "country")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountryEntity {

    /**
     * The primary key (ID) for the Country table.
     * {@code GenerationType.IDENTITY} relies on the database's auto-increment feature.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The official name of the country in Polish.
     * Mapped to a required database column with a maximum length of 100.
     */
    @Column(nullable = false, length = 100)
    @NotNull
    @Size(min = 2, max = 100)
    private String nameInPolish;

    /**
     * The official name of the country in English.
     * Mapped to a required database column with a maximum length of 100.
     */
    @Column(nullable = false, length = 100)
    @NotNull
    @Size(min = 2, max = 100)
    private String nameInEnglish;

    /**
     * The year the country was established. Mapped to a nullable column.
     */
    @Column
    private String establishmentYear;

    /**
     * The year the country was dissolved or cancelled. Mapped to a nullable column.
     */
    @Column
    private String cancellationYear;

    /**
     * Bidirectional One-to-Many relationship with {@code ProvinceEntity}.
     * {@code cascade = CascadeType.ALL} ensures save, update, and delete operations propagate.
     * {@code orphanRemoval = true} ensures that provinces removed from this list are deleted from the database.
     * {@code @JsonManagedReference} indicates this is the "forward" part of the relationship for serialization.
     */
    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ProvinceEntity> provinces = new ArrayList<>();

    /**
     * Bidirectional One-to-Many relationship with {@code MilitaryOrganizationEntity}.
     * Represents all military organizations associated with this country.
     */
    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MilitaryOrganizationEntity> militaryOrganizations = new ArrayList<>();

    /**
     * The URL for the country's flag image. Mapped to a nullable column.
     */
    @Column(length = 2000)
    private String flagImgUrl;

    /**
     * Bidirectional One-to-Many relationship with the history table to track the country's continent association over time.
     * @JsonManagedReference is used here as it's the "owning" side for serialization.
     */
    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CountryContinentHistoryEntity> continentHistory = new ArrayList<>();
}

