package cz.rodro.entity.repository;

import cz.rodro.entity.ContinentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContinentRepository extends JpaRepository<ContinentEntity, Long> {

    /**
     * Finds a continent by its official English name.
     * Useful for initial data setup or lookups.
     * @param name The name of the continent (e.g., "Europe").
     * @return An Optional containing the ContinentEntity.
     */
    Optional<ContinentEntity> findByName(String name);
}
