package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "occupation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OccupationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String occupationName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institution_id")
    @JsonBackReference
    private InstitutionEntity institution;

    @OneToMany(mappedBy = "occupation", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PersonOccupationEntity> personOccupations;

    @Column(columnDefinition = "TEXT")
    private String personImageUrl;

}
