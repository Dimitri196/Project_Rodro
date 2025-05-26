package cz.rodro.service;

import cz.rodro.dto.MilitaryOrganizationDTO;
import cz.rodro.entity.MilitaryOrganizationEntity;

import java.util.List;

public interface MilitaryOrganizationService {

    List<MilitaryOrganizationDTO> getAll();

    MilitaryOrganizationDTO getMilitaryOrganization(long organizationId);

    MilitaryOrganizationDTO addMilitaryOrganization(MilitaryOrganizationDTO militaryOrganizationDTO);

    MilitaryOrganizationDTO removeMilitaryOrganization(long organizationId);

    MilitaryOrganizationDTO updateMilitaryOrganization(Long organizationId, MilitaryOrganizationDTO militaryOrganizationDTO);

    MilitaryOrganizationEntity fetchMilitaryOrganizationById(Long id, String type);

}
