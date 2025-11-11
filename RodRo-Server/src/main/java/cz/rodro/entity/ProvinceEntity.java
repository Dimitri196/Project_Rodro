package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a Province.
 * <p>
 * A Province is a major administrative or geographical subdivision within a {@link CountryEntity}.
 * It maintains a Many-to-One relationship with its parent country and a One-to-Many
 * hierarchical relationship with its child {@link DistrictEntity} entities.
 * </p>
 *
 * @author Dimitri Bodzewicz
 * @version 1.0
 * @see CountryEntity
 * @see DistrictEntity
 */
@Entity(name = "province")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceEntity {

    /**
     * Unique identifier for the Province.
     * <p>
     * This field is the primary key and is automatically generated upon persistence.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The official name of the Province (e.g., "Bohemia", "Moravia").
     * <p>
     * This field cannot be null.
     * </p>
     */
    @Column(nullable = false)
    @NotNull
    private String name;

    /**
     * The parent Country entity this Province belongs to.
     * <p>
     * This establishes a Many-to-One relationship with {@code CountryEntity}.
     * It is lazily fetched and cannot be null. {@code @JsonBackReference} prevents
     * infinite recursion during JSON serialization.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_country_id", nullable = false)
    @JsonBackReference
    private CountryEntity country;

    /**
     * A list of District entities belonging to this Province.
     * <p>
     * This establishes a One-to-Many relationship, and the Province manages the
     * lifecycle of its Districts: all operations cascade, and orphaned District
     * records (those unlinked from the Province) are automatically removed.
     * {@code @JsonManagedReference} is the forward part of the JSON relationship.
     * </p>
     */
    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<DistrictEntity> districts = new ArrayList<>();

    /**
     * URL pointing to an image that represents the Province.
     * <p>
     * The column length is restricted to 2000 characters.
     * </p>
     */
    @Column(length = 2000)
    private String imgUrl;
}
