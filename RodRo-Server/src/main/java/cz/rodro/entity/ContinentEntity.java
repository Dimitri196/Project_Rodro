package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA Entity representing a geographical continent (e.g., Europe, Asia).
 * Serves as a static lookup for the temporal association table.
 */
@Entity
@Table(name = "continent")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContinentEntity {

    /** The primary key for the Continent table. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The primary geographical name of the continent. Must be unique. */
    @Column(nullable = false, unique = true, length = 50)
    private String name;

    /** The name of the continent in Polish. */
    @Column(length = 50)
    private String nameInPolish;

    /**
     * One-to-Many relationship linking to the history table.
     * This field tracks all countries that have ever been associated with this continent.
     */
    @OneToMany(mappedBy = "continent", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Necessary if you want to serialize a continent with its history
    private List<CountryContinentHistoryEntity> countryHistory = new ArrayList<>();
}