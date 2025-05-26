package cz.rodro.service;

import cz.rodro.dto.PersonMilitaryServiceDTO;
import cz.rodro.entity.PersonMilitaryServiceEntity;

import java.util.List;

public interface PersonMilitaryService {

    List<PersonMilitaryServiceDTO> getAll();

    PersonMilitaryServiceDTO getPersonMilitaryService(long serviceId);

    PersonMilitaryServiceDTO addPersonMilitaryService(PersonMilitaryServiceDTO dto);

    void removePersonMilitaryService(long serviceId);

    PersonMilitaryServiceDTO updatePersonMilitaryService(Long serviceId, PersonMilitaryServiceDTO dto);

    PersonMilitaryServiceEntity fetchPersonMilitaryServiceById(Long id, String type);

    List<PersonMilitaryServiceDTO> getByPersonId(Long personId);

}
