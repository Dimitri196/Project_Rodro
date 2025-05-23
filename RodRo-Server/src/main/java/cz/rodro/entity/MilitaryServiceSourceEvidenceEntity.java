package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "military_service_source_evidence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MilitaryServiceSourceEvidenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String note;

    @ManyToOne(optional = false)
    @JsonBackReference("military-service-evidence")
    @JoinColumn(name = "military_service_id")
    private PersonMilitaryServiceEntity militaryService;

    @ManyToOne(optional = false)
    @JsonBackReference("source-military-evidence")
    @JoinColumn(name = "source_id")
    private SourceEntity source;

}