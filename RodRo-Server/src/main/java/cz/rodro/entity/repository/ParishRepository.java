package cz.rodro.entity.repository;

import cz.rodro.entity.ParishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on {@link ParishEntity}.
 * <p>
 * Extends {@link JpaRepository} to provide standard CRUD methods.
 * Custom query methods can be added for more complex searches.
 * </p>
 */
@Repository
public interface ParishRepository extends JpaRepository<ParishEntity, Long> {

    /**
     * Finds all parishes where the parish name contains the given string, ignoring case.
     *
     * @param name The partial parish name to search for.
     * @return A list of matching {@link ParishEntity} objects. Returns an empty list if no matches.
     */
    List<ParishEntity> findByNameContainingIgnoreCase(String name);

}
