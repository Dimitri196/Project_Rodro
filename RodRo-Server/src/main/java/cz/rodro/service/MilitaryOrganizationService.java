package cz.rodro.service;

import cz.rodro.dto.MilitaryOrganizationDTO;
import cz.rodro.entity.MilitaryOrganizationEntity;

import java.util.List;

public interface MilitaryOrganizationService {

    List<MilitaryOrganizationDTO> getAll();
    MilitaryOrganizationDTO getMilitaryOrganization(Long organizationId);
    MilitaryOrganizationDTO addMilitaryOrganization(MilitaryOrganizationDTO militaryOrganizationDTO);
    MilitaryOrganizationDTO removeMilitaryOrganization(Long organizationId);
    MilitaryOrganizationDTO updateMilitaryOrganization(Long organizationId, MilitaryOrganizationDTO militaryOrganizationDTO);
    MilitaryOrganizationEntity fetchMilitaryOrganizationById(Long id, String type);

}
