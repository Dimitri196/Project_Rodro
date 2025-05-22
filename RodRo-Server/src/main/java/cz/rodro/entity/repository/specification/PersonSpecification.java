package cz.rodro.entity.repository.specification;

import cz.rodro.entity.PersonEntity;
import cz.rodro.entity.filter.PersonFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PersonSpecification implements Specification<PersonEntity> {

    private final PersonFilter filter;

    @Override
    public Predicate toPredicate(Root<PersonEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        // Filter by first name (case-insensitive search)
        if (filter.getFirstName() != null && !filter.getFirstName().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("givenName")),
                    "%%" + filter.getFirstName().toLowerCase() + "%%"));
        }

        // Filter by last name (case-insensitive search)
        if (filter.getLastName() != null && !filter.getLastName().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("givenSurname")),
                    "%%" + filter.getLastName().toLowerCase() + "%%"));
        }

        // Filter by birth location
        if (filter.getPersonBirthLocationId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("birthPlace").get("id"), filter.getPersonBirthLocationId()));
        }

        // Filter by baptization location
        if (filter.getPersonBaptizationLocationId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("baptizationPlace").get("id"), filter.getPersonBaptizationLocationId()));
        }

        // Filter by death location
        if (filter.getPersonDeathLocationId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("deathPlace").get("id"), filter.getPersonDeathLocationId()));
        }

        // Filter by burial location
        if (filter.getPersonBurialLocationId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("burialPlace").get("id"), filter.getPersonBurialLocationId()));
        }

        // Filter by birth date
        if (filter.getPersonBirthDate() != null) {
            predicates.add(criteriaBuilder.equal(root.get("birthDate"), filter.getPersonBirthDate()));
        }

        // Filter by baptization date
        if (filter.getPersonBaptizationDate() != null) {
            predicates.add(criteriaBuilder.equal(root.get("baptizationDate"), filter.getPersonBaptizationDate()));
        }

        // Filter by death date
        if (filter.getPersonDeathDate() != null) {
            predicates.add(criteriaBuilder.equal(root.get("deathDate"), filter.getPersonDeathDate()));
        }

        // Filter by burial date
        if (filter.getPersonBurialDate() != null) {
            predicates.add(criteriaBuilder.equal(root.get("burialDate"), filter.getPersonBurialDate()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
