package cz.rodro.entity.repository.specification;

import cz.rodro.entity.*;
import cz.rodro.entity.filter.FamilyFilter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FamilySpecification implements Specification<FamilyEntity> {

    /**
     * The {@link FamilyFilter} containing the filtering criteria.
     */
    private final FamilyFilter filter;

    /**
     * Creates a {@link Predicate} representing the filtering criteria for querying {@link FamilyEntity}.
     * This method dynamically builds a query based on the values in the provided {@link FamilyFilter}.
     *
     * @param root The root of the query (representing {@link FamilyEntity}).
     * @param criteriaQuery The criteria query object.
     * @param criteriaBuilder The criteria builder used to create the predicates.
     * @return A combined {@link Predicate} that represents the filter conditions.
     */
    @Override
    public Predicate toPredicate(Root<FamilyEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        // Filter by marriage location
        if (filter.getMarriageLocationID() != null) {
            Join<LocationEntity, FamilyEntity> locationJoin = root.join(FamilyEntity_.MARRIAGE_LOCATION);
            predicates.add(criteriaBuilder.equal(locationJoin.get(LocationEntity_.ID), filter.getMarriageLocationID()));
        }

        // Filter by male spouse
        if (filter.getSpouseMaleID() != null) {
            Join<PersonEntity, FamilyEntity> maleSpouseJoin = root.join(FamilyEntity_.SPOUSE_MALE);
            predicates.add(criteriaBuilder.equal(maleSpouseJoin.get(PersonEntity_.ID), filter.getSpouseMaleID()));
        }

        // Filter by female spouse
        if (filter.getSpouseFemaleID() != null) {
            Join<PersonEntity, FamilyEntity> femaleSpouseJoin = root.join(FamilyEntity_.SPOUSE_FEMALE);
            predicates.add(criteriaBuilder.equal(femaleSpouseJoin.get(PersonEntity_.ID), filter.getSpouseFemaleID()));
        }

        // Filter by marital status for male spouse
        if (filter.getMaritalStatusForSpouseMale() != null) {
            predicates.add(criteriaBuilder.equal(root.get(FamilyEntity_.MARITAL_STATUS_FOR_SPOUSE_MALE),
                    filter.getMaritalStatusForSpouseMale()));
        }

        // Filter by marital status for female spouse
        if (filter.getMaritalStatusForSpouseFemale() != null) {
            predicates.add(criteriaBuilder.equal(root.get(FamilyEntity_.MARITAL_STATUS_FOR_SPOUSE_FEMALE),
                    filter.getMaritalStatusForSpouseFemale()));
        }

        // Filter by marriage date
        if (filter.getMarriageDate() != null) {
            predicates.add(criteriaBuilder.equal(root.get(FamilyEntity_.MARRIAGE_DATE), filter.getMarriageDate()));
        }

        // Filter by note content (case-insensitive search)
        if (filter.getNote() != null && !filter.getNote().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(FamilyEntity_.NOTE)),
                    "%" + filter.getNote().toLowerCase() + "%"));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
