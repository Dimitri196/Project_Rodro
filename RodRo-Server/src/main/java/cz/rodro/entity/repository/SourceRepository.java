package cz.rodro.entity.repository;

import cz.rodro.constant.SourceType;
import cz.rodro.entity.LocationHistoryEntity;
import cz.rodro.entity.SourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SourceRepository extends JpaRepository<SourceEntity, Long> {

}
