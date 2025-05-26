// src/main/java/cz/rodro/service/MilitaryStructureServiceImpl.java
package cz.rodro.service;

import cz.rodro.dto.MilitaryStructureDTO;
import cz.rodro.dto.mapper.MilitaryStructureMapper;
import cz.rodro.entity.MilitaryStructureEntity;
import cz.rodro.entity.repository.MilitaryStructureRepository;
import cz.rodro.exception.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MilitaryStructureServiceImpl implements MilitaryStructureService {

    @Autowired
    MilitaryStructureRepository militaryStructureRepository;

    @Autowired
    MilitaryStructureMapper militaryStructureMapper;

    @Override
    public List<MilitaryStructureDTO> getAll() {
        return militaryStructureRepository
                .findAll()
                .stream()
                .map(militaryStructureMapper::toMilitaryStructureDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MilitaryStructureDTO getMilitaryStructure(long structureId) {
        MilitaryStructureEntity entity = militaryStructureRepository.getReferenceById(structureId);
        return militaryStructureMapper.toMilitaryStructureDTO(entity);
    }

    @Override
    public MilitaryStructureDTO addMilitaryStructure(MilitaryStructureDTO dto) {
        MilitaryStructureEntity entity = militaryStructureMapper.toMilitaryStructureEntity(dto);
        entity = militaryStructureRepository.save(entity);
        return militaryStructureMapper.toMilitaryStructureDTO(entity);
    }

    @Override
    public MilitaryStructureDTO removeMilitaryStructure(long structureId) {
        MilitaryStructureEntity entity = militaryStructureRepository.findById(structureId)
                .orElseThrow(EntityNotFoundException::new);
        MilitaryStructureDTO dto = militaryStructureMapper.toMilitaryStructureDTO(entity);
        militaryStructureRepository.delete(entity);
        return dto;
    }

    @Override
    @Transactional
    public MilitaryStructureDTO updateMilitaryStructure(Long structureId, MilitaryStructureDTO dto) {
        MilitaryStructureEntity entity = militaryStructureRepository.findById(structureId)
                .orElseThrow(() -> new NotFoundException("MilitaryStructure with id " + structureId + " wasn't found in the database"));
        militaryStructureMapper.updateMilitaryStructureEntity(dto, entity);
        militaryStructureRepository.save(entity);
        return militaryStructureMapper.toMilitaryStructureDTO(entity);
    }

    @Override
    public MilitaryStructureEntity fetchMilitaryStructureById(Long id, String type) {
        return militaryStructureRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(type + " with id " + id + " wasn't found in the database."));
    }
}