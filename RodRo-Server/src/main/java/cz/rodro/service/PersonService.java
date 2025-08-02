package cz.rodro.service;

import cz.rodro.dto.*;
import cz.rodro.entity.FamilyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

// PersonService Interface
public interface PersonService {

    PersonDTO addPerson(PersonDTO personDTO);
    PersonDTO getPerson(long personId);
    void removePerson(long personId);

    // New method for paginated, filtered, and sorted list of persons
    // Using fully qualified name for Pageable to avoid ambiguity
    Page<PersonListProjection> getAllPersons(String searchTerm, org.springframework.data.domain.Pageable pageable);

    PersonDTO updatePerson(Long id, PersonDTO personDTO);
    List<FamilyEntity> getSpousesAsMale(Long personId);
    List<FamilyEntity> getSpousesAsFemale(Long personId);
    List<FamilyEntity> getSpouses(Long personId);
    List<PersonSourceEvidenceDTO> getSourceEvidences(Long personId);
    List<PersonDTO> getSiblings(Long personId);

}
