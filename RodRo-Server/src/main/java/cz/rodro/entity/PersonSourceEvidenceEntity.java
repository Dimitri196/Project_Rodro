package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "person_source_evidence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonSourceEvidenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String personName;
    private String sourceName;

    @ManyToOne(optional = false)
    @JsonBackReference("person-source-evidence")
    @JoinColumn(name = "person_id", nullable = false)
    private PersonEntity person;

    @ManyToOne(optional = false)
    @JsonBackReference("source-source-evidence")
    @JoinColumn(name = "source_id", nullable = false)
    private SourceEntity source;

}