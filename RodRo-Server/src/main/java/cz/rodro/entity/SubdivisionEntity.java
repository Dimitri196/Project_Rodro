package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "subdivision")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubdivisionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String subdivisionName;

    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private DistrictEntity district;

    @ManyToOne
    @JoinColumn(name = "administrative_center_id", nullable = false)
    @JsonManagedReference
    private LocationEntity administrativeCenter;

    @Column(name = "establishment_year")
    private String subdivisionEstablishmentYear;

    @Column(name = "cancellation_year")
    private String subdivisionCancellationYear;

    @OneToMany(mappedBy = "subdivision")
    private List<LocationHistoryEntity> historyEntries;
}
