package cz.rodro.entity.repository.specification;

import cz.rodro.entity.LocationEntity;
import cz.rodro.entity.filter.LocationFilter;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class LocationSpecification implements Specification<LocationEntity> {

    private final LocationFilter filter;

    @Override
    public Predicate toPredicate(Root<LocationEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        // Filter by name (case-insensitive search)
        if (filter.getName() != null && !filter.getName().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                    "%" + filter.getName().toLowerCase() + "%"));
        }

        // Filter by district (case-insensitive search)
        if (filter.getDistrict() != null && !filter.getDistrict().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("district")),
                    "%" + filter.getDistrict().toLowerCase() + "%"));
        }

        // Filter by region (case-insensitive search)
        if (filter.getRegion() != null && !filter.getRegion().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("region")),
                    "%" + filter.getRegion().toLowerCase() + "%"));
        }

        // Filter by country (case-insensitive search)
        if (filter.getCountry() != null && !filter.getCountry().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("country")),
                    "%" + filter.getCountry().toLowerCase() + "%"));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}

