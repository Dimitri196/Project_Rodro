package cz.rodro.service;

import cz.rodro.dto.MilitaryRankDTO;
import cz.rodro.dto.PersonMilitaryServiceDTO;
import cz.rodro.dto.mapper.MilitaryRankMapper;
import cz.rodro.dto.mapper.PersonMilitaryServiceMapper;
import cz.rodro.entity.MilitaryOrganizationEntity;
import cz.rodro.entity.MilitaryRankEntity;
import cz.rodro.entity.MilitaryStructureEntity;
import cz.rodro.entity.repository.MilitaryOrganizationRepository;
import cz.rodro.entity.repository.MilitaryRankRepository;
import cz.rodro.entity.repository.MilitaryStructureRepository;
import cz.rodro.entity.repository.PersonMilitaryServiceRepository;
import cz.rodro.exception.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MilitaryRankServiceImpl implements MilitaryRankService {

    @Autowired
    private MilitaryRankRepository militaryRankRepository;

    @Autowired
    private MilitaryRankMapper militaryRankMapper;

    @Autowired
    private PersonMilitaryServiceRepository personMilitaryServiceRepository;

    @Autowired
    private PersonMilitaryServiceMapper personMilitaryServiceMapper;

    @Autowired
    private MilitaryOrganizationRepository militaryOrganizationRepository;

    @Autowired
    private MilitaryStructureRepository militaryStructureRepository; // New dependency

    @Override
    public List<MilitaryRankDTO> getAll() {
        return militaryRankRepository
                .findAll()
                .stream()
                .map(militaryRankMapper::toMilitaryRankDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MilitaryRankDTO getMilitaryRank(Long id) {
        MilitaryRankEntity entity = militaryRankRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("MilitaryRank with id " + id + " wasn't found."));
        return militaryRankMapper.toMilitaryRankDTO(entity);
    }

    @Override
    public MilitaryRankDTO getMilitaryRankWithPersons(Long id) {
        MilitaryRankEntity entity = militaryRankRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("MilitaryRank with id " + id + " wasn't found."));

        MilitaryRankDTO dto = militaryRankMapper.toMilitaryRankDTO(entity);

        List<PersonMilitaryServiceDTO> persons = personMilitaryServiceRepository.findAllByMilitaryRankId(id)
                .stream()
                .map(personMilitaryServiceMapper::toDto)
                .collect(Collectors.toList());

        dto.setPersons(persons);
        return dto;
    }

    @Override
    @Transactional
    public MilitaryRankDTO addMilitaryRank(MilitaryRankDTO dto) {
        MilitaryRankEntity entity = militaryRankMapper.toMilitaryRankEntity(dto);

        // Logic to set MilitaryOrganizationEntity
        if (dto.getMilitaryOrganization() != null && dto.getMilitaryOrganization().getId() != null) {
            MilitaryOrganizationEntity organizationEntity = militaryOrganizationRepository.findById(dto.getMilitaryOrganization().getId())
                    .orElseThrow(() -> new NotFoundException("MilitaryOrganization with id " + dto.getMilitaryOrganization().getId() + " wasn't found."));
            entity.setMilitaryOrganization(organizationEntity);
        } else {
            entity.setMilitaryOrganization(null);
        }

        // NEW LOGIC: Set MilitaryStructureEntity
        if (dto.getMilitaryStructureDTO() != null && dto.getMilitaryStructureDTO().getId() != null) {
            MilitaryStructureEntity structureEntity = militaryStructureRepository.findById(dto.getMilitaryStructureDTO().getId())
                    .orElseThrow(() -> new NotFoundException("MilitaryStructure with id " + dto.getMilitaryStructureDTO().getId() + " wasn't found."));
            entity.setMilitaryStructure(structureEntity);
        } else {
            entity.setMilitaryStructure(null);
        }

        entity = militaryRankRepository.save(entity);
        return militaryRankMapper.toMilitaryRankDTO(entity);
    }

    @Override
    public MilitaryRankDTO removeMilitaryRank(Long rankId) {
        MilitaryRankEntity entity = militaryRankRepository.findById(rankId)
                .orElseThrow(EntityNotFoundException::new);
        MilitaryRankDTO dto = militaryRankMapper.toMilitaryRankDTO(entity);
        militaryRankRepository.delete(entity);
        return dto;
    }

    @Override
    @Transactional
    public MilitaryRankDTO updateMilitaryRank(Long rankId, MilitaryRankDTO dto) {
        MilitaryRankEntity entity = militaryRankRepository.findById(rankId)
                .orElseThrow(() -> new NotFoundException("MilitaryRank with id " + rankId + " wasn't found in the database"));

        // Logic to update MilitaryOrganizationEntity
        if (dto.getMilitaryOrganization() != null && dto.getMilitaryOrganization().getId() != null) {
            MilitaryOrganizationEntity organizationEntity = militaryOrganizationRepository.findById(dto.getMilitaryOrganization().getId())
                    .orElseThrow(() -> new NotFoundException("MilitaryOrganization with id " + dto.getMilitaryOrganization().getId() + " wasn't found."));
            entity.setMilitaryOrganization(organizationEntity);
        } else {
            entity.setMilitaryOrganization(null);
        }

        // NEW LOGIC: Update MilitaryStructureEntity
        if (dto.getMilitaryStructureDTO() != null && dto.getMilitaryStructureDTO().getId() != null) {
            MilitaryStructureEntity structureEntity = militaryStructureRepository.findById(dto.getMilitaryStructureDTO().getId())
                    .orElseThrow(() -> new NotFoundException("MilitaryStructure with id " + dto.getMilitaryStructureDTO().getId() + " wasn't found."));
            entity.setMilitaryStructure(structureEntity);
        } else {
            entity.setMilitaryStructure(null);
        }

        militaryRankMapper.updateMilitaryRankEntity(dto, entity);
        militaryRankRepository.save(entity);
        return militaryRankMapper.toMilitaryRankDTO(entity);
    }

    @Override
    public MilitaryRankEntity fetchMilitaryRankById(Long id, String type) {
        return militaryRankRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(type + " with id " + id + " wasn't found in the database."));
    }
}
