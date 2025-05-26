package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "country")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CountryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotNull
    @Size(min = 2, max = 100)
    private String countryNameInPolish;

    @Column(nullable = false, length = 100)
    @NotNull
    @Size(min = 2, max = 100)
    private String countryNameInEnglish;

    @Column
    private String countryEstablishmentYear;

    @Column
    private String countryCancellationYear;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ProvinceEntity> provinces = new ArrayList<>();

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MilitaryStructureEntity> militaryStructures = new ArrayList<>();
}
