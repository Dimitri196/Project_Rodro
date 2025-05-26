package cz.rodro.service;

import cz.rodro.dto.MilitaryRankDTO;
import cz.rodro.dto.mapper.MilitaryRankMapper;
import cz.rodro.entity.MilitaryRankEntity;
import cz.rodro.entity.repository.MilitaryRankRepository;
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
    MilitaryRankRepository militaryRankRepository;

    @Autowired
    MilitaryRankMapper militaryRankMapper;

    @Override
    public List<MilitaryRankDTO> getAll() {
        return militaryRankRepository
                .findAll()
                .stream()
                .map(militaryRankMapper::toMilitaryRankDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MilitaryRankDTO getMilitaryRank(long rankId) {
        MilitaryRankEntity entity = militaryRankRepository.getReferenceById(rankId);
        return militaryRankMapper.toMilitaryRankDTO(entity);
    }

    @Override
    public MilitaryRankDTO addMilitaryRank(MilitaryRankDTO dto) {
        MilitaryRankEntity entity = militaryRankMapper.toMilitaryRankEntity(dto);
        entity = militaryRankRepository.save(entity);
        return militaryRankMapper.toMilitaryRankDTO(entity);
    }

    @Override
    public MilitaryRankDTO removeMilitaryRank(long rankId) {
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