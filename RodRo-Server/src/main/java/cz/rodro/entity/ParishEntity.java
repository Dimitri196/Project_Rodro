package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "parish")
@Getter
@Setter
public class ParishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String parishName;
    private String parishMainChurchName; // make ChurchEntity with private ChurchEntity churches;
    private LocalDate establishmentDate;
    private LocalDate cancellationDate;

    @ManyToOne
    @JoinColumn(name = "parish_location_id")
    @JsonManagedReference  // This annotation helps prevent circular references when serializing JSON
    private LocationEntity parishLocation;  // Correctly mapped to LocationEntity for JPA

    @OneToMany(mappedBy = "parish", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<ParishLocationEntity> parishLocations;
}