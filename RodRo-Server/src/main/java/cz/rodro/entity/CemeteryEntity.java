package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "cemetery")
public class CemeteryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Cemetery's unique ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cemetery_location_id")
    @JsonManagedReference
    private LocationEntity cemeteryLocation;  // Foreign key to the location

    @Column(nullable = false)
    private String cemeteryName;  // Name of the cemetery (required)

    @Column
    private String description;  // Description of the cemetery (optional)

    @Column
    private String webLink;  // Web link (URL) related to the cemetery (optional)
}