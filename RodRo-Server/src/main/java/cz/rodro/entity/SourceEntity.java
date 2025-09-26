package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import cz.rodro.constant.SourceType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity class representing a Source in the system.
 *
 * <p>This entity stores metadata about a source such as its title, description,
 * reference, type, URL, and its relationship to locations and evidences.</p>
 *
 * <p>It is mapped to the database table {@code source}.</p>
 */
@Entity
@Table(name = "source")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SourceEntity {

    /**
     * Primary key of the source.
     * <p>Auto-generated using {@link GenerationType#IDENTITY} strategy.</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Title of the source.
     * <p>This field is mandatory and cannot be blank.</p>
     */
    @NotBlank(message = "Source title is required")
    private String title;

    /**
     * Detailed description of the source.
     * <p>Stored as a large object (LOB) in the database to support longer text.</p>
     */
    @Lob
    private String description;

    /**
     * Reference or citation of the source (e.g., bibliographic reference).
     * <p>Maximum length: 255 characters.</p>
     */
    @Size(max = 255)
    private String reference;

    /**
     * Type of the source (e.g., BOOK, ARTICLE, WEBSITE).
     * <p>Stored as a string value in the database. Cannot be null.</p>
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SourceType type;

    /**
     * URL pointing to the source, if applicable.
     * <p>Must be a valid URL format if provided.</p>
     */
    @URL(message = "Invalid URL")
    private String url;

    /**
     * Collection of evidence entities linking people to this source.
     * <p>Represents evidence connections between people and this source.</p>
     * <ul>
     *   <li>{@code mappedBy = "source"} → inverse side of the relationship.</li>
     *   <li>{@code CascadeType.ALL} → all persistence operations cascade to child entities.</li>
     *   <li>{@code orphanRemoval = true} → child entities are removed if no longer referenced.</li>
     * </ul>
     */
    @OneToMany(mappedBy = "source", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("source-source-evidence")
    private List<PersonSourceEvidenceEntity> evidences = new ArrayList<>();

    /**
     * Location associated with this source.
     * <p>Many sources can belong to one location (Many-to-One relationship).</p>
     * <ul>
     *   <li>{@code fetch = FetchType.LAZY} → location data is loaded only when accessed.</li>
     *   <li>{@code nullable = false} → every source must have an associated location.</li>
     * </ul>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_location_id", nullable = false)
    @JsonBackReference
    private LocationEntity location;
}
