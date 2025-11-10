package cz.rodro.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "military_structure")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MilitaryStructureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unit_name", nullable = false, length = 255)
    private String name;

    @Column(name = "unit_type", nullable = false, length = 100)
    private String type;

    private String activeFromYear;
    private String activeToYear;

    @Column(length = 1000)
    private String notes;

    @Column(nullable = true)
    private String bannerImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "military_organization_id")
    @JsonBackReference("organization-structures")
    private MilitaryOrganizationEntity organization;


}
