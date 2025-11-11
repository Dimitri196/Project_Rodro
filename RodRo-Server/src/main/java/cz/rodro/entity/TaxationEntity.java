package cz.rodro.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




// In development - taxation types for occupations and institutions // e.g., guild tax, church tax, etc. Prof. Dr. Hans Ruttke

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tax")
public class TaxationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String taxationName;

    @Column
    private String taxDescription;
}
