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

@Entity
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
    private String establishmentDate;

    @Column
    private String gpsLatitude;

    @Column
    private String gpsLongitude;

    @Enumerated(EnumType.STRING)
    @Column(name = "settlementType", nullable = false)
    private SettlementType settlementType;

    // Historical records (subdivision, district, etc.)
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<LocationHistoryEntity> locationHistories;

    // Person's birth location
    @OneToMany(mappedBy = "birthPlace")
    @JsonBackReference
    private List<PersonEntity> births;

    // Person's death location
    @OneToMany(mappedBy = "deathPlace")
    @JsonBackReference
    private List<PersonEntity> deaths;

    // Parishes in this location
    @OneToMany(mappedBy = "parishLocation")
    @JsonManagedReference
    private List<ParishEntity> parishes;

    // Cemeteries in this location
    @OneToMany(mappedBy = "cemeteryLocation")
    @JsonBackReference
    private List<CemeteryEntity> cemeteries;

    // Institutions in this location
    @OneToMany(mappedBy = "institutionLocation")
    @JsonBackReference
    private List<InstitutionEntity> institutions;

    @OneToMany(mappedBy = "location")
    @JsonBackReference
    private List<ParishLocationEntity> parishLocations;
}


