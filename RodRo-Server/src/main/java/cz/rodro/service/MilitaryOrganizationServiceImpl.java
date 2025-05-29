package cz.rodro.service;

import cz.rodro.dto.MilitaryOrganizationDTO;
import cz.rodro.dto.mapper.MilitaryOrganizationMapper;
import cz.rodro.entity.MilitaryOrganizationEntity;
import cz.rodro.entity.repository.MilitaryOrganizationRepository;
import cz.rodro.exception.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MilitaryOrganizationServiceImpl implements MilitaryOrganizationService {

    @Autowired
    MilitaryOrganizationRepository militaryOrganizationRepository;

    @Autowired
    MilitaryOrganizationMapper militaryOrganizationMapper;

    @Override
    public List<MilitaryOrganizationDTO> getAll() {
        return militaryOrganizationRepository
                .findAll()
                .stream()
                .map(militaryOrganizationMapper::toMilitaryOrganizationDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MilitaryOrganizationDTO getMilitaryOrganization(Long organizationId) {
        MilitaryOrganizationEntity entity = militaryOrganizationRepository.getReferenceById(organizationId);
        return militaryOrganizationMapper.toMilitaryOrganizationDTO(entity);
    }

    @Override
    public MilitaryOrganizationDTO addMilitaryOrganization(MilitaryOrganizationDTO dto) {
        MilitaryOrganizationEntity entity = militaryOrganizationMapper.toMilitaryOrganizationEntity(dto);
        entity = militaryOrganizationRepository.save(entity);
        return militaryOrganizationMapper.toMilitaryOrganizationDTO(entity);
    }

    @Override
    public MilitaryOrganizationDTO removeMilitaryOrganization(Long organizationId) {
        MilitaryOrganizationEntity entity = militaryOrganizationRepository.findById(organizationId)
                .orElseThrow(EntityNotFoundException::new);
        MilitaryOrganizationDTO dto = militaryOrganizationMapper.toMilitaryOrganizationDTO(entity);
        militaryOrganizationRepository.delete(entity);
        return dto;
    }

    @Override
    @Transactional
    public MilitaryOrganizationDTO updateMilitaryOrganization(Long organizationId, MilitaryOrganizationDTO dto) {
        MilitaryOrganizationEntity entity = militaryOrganizationRepository.findById(organizationId)
                .orElseThrow(() -> new NotFoundException("MilitaryOrganization with id " + organizationId + " wasn't found in the database"));
        militaryOrganizationMapper.updateMilitaryOrganizationEntity(dto, entity);
        militaryOrganizationRepository.save(entity);
        return militaryOrganizationMapper.toMilitaryOrganizationDTO(entity);
    }

    @Override
    public MilitaryOrganizationEntity fetchMilitaryOrganizationById(Long id, String type) {
        return militaryOrganizationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(type + " with id " + id + " wasn't found in the database."));
    }
}
