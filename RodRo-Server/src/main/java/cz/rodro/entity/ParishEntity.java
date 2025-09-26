package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import cz.rodro.constant.ConfessionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity(name = "parish")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String mainChurchName;

    private Integer establishmentYear;

    private Integer cancellationYear;

    @Column(length = 2000)
    private String churchImageUrl;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ConfessionType confession;

    /**
     * List of parish-location links connecting this parish to specific locations.
     * Deleting a parish will also delete associated ParishLocationEntity records.
     */
    @OneToMany(mappedBy = "parish", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<ParishLocationEntity> locations;
}
