package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vladmihalcea.hibernate.type.json.JsonType;
import cz.rodro.constant.ConfessionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.URL;
import cz.rodro.constant.SourceType;

import java.util.*;

/**
 * Entity representing a historical or archival source document or record.
 * <p>
 * This entity captures metadata, temporal data, citation information, and flexible
 * JSON-based metadata for various types of sources used in the genealogical project.
 * It is linked to a specific {@link LocationEntity}.
 * </p>
 */
@Entity
@Table(name = "source")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // Highly recommended for easier entity creation
public class SourceEntity {

    /**
     * Unique identifier for the Source.
     * <p>
     * This field is the primary key and is automatically generated upon persistence.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The title or short name of the source document/record.
     * <p>
     * This field is mandatory and must not be blank.
     * </p>
     */
    @NotBlank(message = "Source title is required")
    private String title;

    /**
     * A detailed description or notes about the source.
     * <p>
     * Mapped as a large object (LOB) to accommodate extensive text.
     * </p>
     */
    @Lob
    private String description;

    /**
     * A brief, shorthand reference used for quick identification of the source.
     * <p>
     * Maximum size is 255 characters.
     * </p>
     */
    @Size(max = 255)
    private String reference;

    /**
     * The type of the source (e.g., ARCHIVE, REGISTER, BOOK).
     * <p>
     * Mapped as an enumerated String and cannot be null.
     * </p>
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SourceType type;

    /**
     * The religious confession or denomination associated with the source (e.g., CATHOLIC, LUTHERAN).
     * <p>
     * Mapped as an enumerated String and cannot be null.
     * </p>
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConfessionType confession;

    /**
     * A URL linking to the source online, if available.
     * <p>
     * Validated to ensure it is a properly formatted URL.
     * </p>
     */
    @URL(message = "Invalid URL")
    private String url;

    /**
     * The estimated or known year the source was created.
     */
    private Integer creationYear;

    /**
     * The earliest year covered by the source's data.
     */
    private Integer startYear;

    /**
     * The latest year covered by the source's data.
     */
    private Integer endYear;

    /**
     * Flexible, schema-less metadata specific to the source type.
     * <p>
     * Stored as a JSON object in the database column. Initializes as an empty map.
     * </p>
     */
    @Type(value = JsonType.class)
    @Column(columnDefinition = "json")
    private Map<String, Object> metadata = new HashMap<>();

    /**
     * A full, standardized citation string (e.g., Chicago or MLA format).
     * <p>
     * Mapped as a large object (LOB) to store the full citation text.
     * </p>
     */
    @Lob
    private String citationString;

    /**
     * List of evidence links connecting this source to specific historical events or persons.
     * <p>
     * **DEPRECATED:** This relationship may be subject to future restructuring.
     * The relationship is managed on this side, with cascade operations and orphan removal.
     * {@code @JsonManagedReference} is the forward part of the JSON relationship.
     * </p>
     * @deprecated Consider restructuring the evidence model for flexibility.
     */
    @Deprecated
    @OneToMany(mappedBy = "source", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("source-source-evidence")
    private List<PersonSourceEvidenceEntity> evidences = new ArrayList<>();

    /**
     * The {@link LocationEntity} this source primarily documents or is associated with.
     * <p>
     * This establishes a Many-to-One relationship. It is lazily fetched and cannot be null.
     * {@code @JsonBackReference} prevents infinite recursion during JSON serialization.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_location_id", nullable = false)
    @JsonBackReference
    private LocationEntity location;

}
