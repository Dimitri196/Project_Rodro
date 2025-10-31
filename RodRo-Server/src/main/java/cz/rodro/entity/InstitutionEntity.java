package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import cz.rodro.constant.InstitutionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "institution")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Institution name is required")
    private String institutionName;

    @Column(columnDefinition = "TEXT")
    private String institutionDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institution_location_id")
    @JsonManagedReference
    private LocationEntity institutionLocation;

    @OneToMany(mappedBy = "institution", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OccupationEntity> occupations = new ArrayList<>();

    @Column(columnDefinition = "TEXT")
    private String sealImageUrl;

    @Enumerated(EnumType.STRING) // store as text in DB, not as ordinal
    @Column(nullable = false)
    private InstitutionType institutionType;

}
