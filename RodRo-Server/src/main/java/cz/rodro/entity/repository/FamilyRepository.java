package cz.rodro.entity.repository;

import cz.rodro.entity.FamilyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FamilyRepository extends JpaRepository<FamilyEntity, Long>, JpaSpecificationExecutor<FamilyEntity> {

    // Fetch families where the person is the male spouse
    @Query("SELECT f FROM family f WHERE f.spouseMale.id = :personId")
    List<FamilyEntity> findBySpouseMaleId(@Param("personId") Long personId);

    // Fetch families where the person is the female spouse
    @Query("SELECT f FROM family f WHERE f.spouseFemale.id = :personId")
    List<FamilyEntity> findBySpouseFemaleId(@Param("personId") Long personId);

    List<FamilyEntity> findBySpouseMaleIdOrSpouseFemaleId(Long maleId, Long femaleId);

    @Query("SELECT f FROM family f JOIN f.children c WHERE c.id = :childId")
    List<FamilyEntity> findByChildrenId(@Param("childId") Long childId);


}
