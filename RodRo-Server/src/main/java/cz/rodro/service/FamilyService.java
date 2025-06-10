package cz.rodro.service;

import cz.rodro.dto.FamilyDTO;
import cz.rodro.entity.filter.FamilyFilter;

import java.util.List;

public interface FamilyService {

    FamilyDTO addFamily(FamilyDTO familyDTO);
    FamilyDTO getFamily(Long familyId);
    FamilyDTO updateFamily(Long familyId, FamilyDTO familyDTO);
    void removeFamily(Long familyId);

   List<FamilyDTO> getAllFamilies(FamilyFilter familyFilter);

    List<FamilyDTO> getAllFamilies();

    List<FamilyDTO> findFamiliesByChild(Long personId);

    List<FamilyDTO> getSpouses(Long personId);

}
