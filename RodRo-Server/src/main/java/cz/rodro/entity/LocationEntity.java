package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import cz.rodro.constant.SettlementType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity(name = "location") // Using 'name' attribute for entity name
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "location",
        uniqueConstraints = @UniqueConstraint(columnNames = "locationName")
)
public class LocationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String locationName;

    @Column
    private Integer establishmentYear;

    @Column
    private Double gpsLatitude;

    @Column
    private Double gpsLongitude;

    @Enumerated(EnumType.STRING)
    @Column(name = "settlementType", nullable = false)
    private SettlementType settlementType;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<LocationHistoryEntity> locationHistories;

    @OneToMany(mappedBy = "sourceLocation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SourceEntity> sources;

    @OneToMany(mappedBy = "birthPlace")
    @JsonBackReference
    private List<PersonEntity> births;

    @OneToMany(mappedBy = "deathPlace")
    @JsonBackReference
    private List<PersonEntity> deaths;

    // This is the CORRECT relationship: it links to the join table entity.
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<ParishLocationEntity> parishLocations;

    // Cemeteries in this location
    @OneToMany(mappedBy = "cemeteryLocation")
    @JsonBackReference
    private List<CemeteryEntity> cemeteries;

    // Institutions in this location
    @OneToMany(mappedBy = "institutionLocation")
    @JsonBackReference
    private List<InstitutionEntity> institutions;
}
