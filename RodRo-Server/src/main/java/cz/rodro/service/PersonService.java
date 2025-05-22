package cz.rodro.service;

import cz.rodro.dto.PersonDTO;
import cz.rodro.dto.PersonSourceEvidenceDTO;
import cz.rodro.dto.ProvinceDTO;
import cz.rodro.entity.FamilyEntity;

import java.util.List;

public interface PersonService {

    PersonDTO addPerson(PersonDTO personDTO);
    void removePerson(long id);
    List<PersonDTO> getAll();
    PersonDTO getPerson(long personId);
    PersonDTO updatePerson(Long personId, PersonDTO personDTO);

    List<FamilyEntity> getSpousesAsMale(Long personId);
    List<FamilyEntity> getSpousesAsFemale(Long personId);
    List<FamilyEntity> getSpouses(Long personId);
    List<PersonSourceEvidenceDTO> getSourceEvidences(Long personId);

}
