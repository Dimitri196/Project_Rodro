package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "province")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProvinceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String provinceName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_country_id", nullable = false)
    @JsonBackReference
    private CountryEntity country;

    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<DistrictEntity> districts = new ArrayList<>();

    @Column
    private String provinceFlagImgUrl;
}


