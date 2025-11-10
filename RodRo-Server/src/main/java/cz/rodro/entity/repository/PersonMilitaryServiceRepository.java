package cz.rodro.entity.repository;

import cz.rodro.entity.PersonMilitaryServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonMilitaryServiceRepository extends JpaRepository<PersonMilitaryServiceEntity, Long> {

    /**
     * Finds all military service records associated with a specific person ID.
     */
    List<PersonMilitaryServiceEntity> findByPersonId(Long personId);

    /**
     * Finds all military service records associated with a specific rank ID.
     * (Retained the original name for simplicity, though findByMilitaryRankId is also standard.)
     */
    List<PersonMilitaryServiceEntity> findAllByMilitaryRankId(Long rankId);

    /**
     * Finds all military service records associated with a specific structure ID.
     */
    List<PersonMilitaryServiceEntity> findByMilitaryStructureId(Long structureId);


    // --- Eager Fetching for Detail Views ---

    /**
     * Finds a single military service record by its ID and eagerly fetches all related entities:
     * Person, Rank, and Structure.
     * This is essential for preventing LazyInitializationException when creating the detailed DTO.
     */
    @Query("""
        SELECT pms FROM PersonMilitaryServiceEntity pms
        JOIN FETCH pms.person
        JOIN FETCH pms.militaryRank mr
        JOIN FETCH pms.militaryStructure ms
        WHERE pms.id = :id
        """)
    Optional<PersonMilitaryServiceEntity> findByIdWithDetails(@Param("id") Long id);
}