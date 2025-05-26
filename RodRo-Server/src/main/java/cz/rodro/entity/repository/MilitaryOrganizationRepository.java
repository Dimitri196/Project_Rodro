package cz.rodro.entity.repository;

import cz.rodro.entity.MilitaryOrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MilitaryOrganizationRepository extends JpaRepository<MilitaryOrganizationEntity, Long> {
}