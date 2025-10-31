package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.URL;
import cz.rodro.constant.SourceType;

import java.util.*;

@Entity
@Table(name = "source")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // Highly recommended for easier entity creation
public class SourceEntity {

    // --- CORE RELATIONAL FIELDS ---

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Source title is required")
    private String title;

    @Lob
    private String description;

    @Size(max = 255)
    private String reference;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SourceType type;

    @URL(message = "Invalid URL")
    private String url;

    private Integer creationYear;
    private Integer startYear;
    private Integer endYear;

    // --- FLEXIBLE METADATA FIELD (Hybrid Schema) ---

    @Type(value = JsonType.class)
    @Column(columnDefinition = "json") // Use generic 'json' instead of 'jsonb'
    private Map<String, Object> metadata = new HashMap<>();

    // --- CITATION FIELD ---

    @Lob
    private String citationString;


    // --- RELATIONSHIPS ---

    @Deprecated
    @OneToMany(mappedBy = "source", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("source-source-evidence")
    private List<PersonSourceEvidenceEntity> evidences = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_location_id", nullable = false)
    @JsonBackReference
    private LocationEntity location;

}
