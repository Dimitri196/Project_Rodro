package cz.rodro.service;

import cz.rodro.dto.*;
import cz.rodro.entity.FamilyEntity;

import java.util.List;

public interface PersonService {

    PersonDTO addPerson(PersonDTO personDTO);

    PersonDTO getPerson(long personId);

    void removePerson(long personId);

    List<PersonDTO> getAll();

    PersonDTO updatePerson(Long id, PersonDTO personDTO);

    List<FamilyEntity> getSpousesAsMale(Long personId);

    List<FamilyEntity> getSpousesAsFemale(Long personId);

    List<FamilyEntity> getSpouses(Long personId);

    List<PersonSourceEvidenceDTO> getSourceEvidences(Long personId);

    List<PersonDTO> getSiblings(Long personId);

}