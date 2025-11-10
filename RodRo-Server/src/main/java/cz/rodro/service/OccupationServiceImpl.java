package cz.rodro.service;

import cz.rodro.dto.OccupationDTO;
import cz.rodro.dto.mapper.OccupationMapper;
import cz.rodro.entity.InstitutionEntity;
import cz.rodro.entity.OccupationEntity;
import cz.rodro.entity.repository.InstitutionRepository;
import cz.rodro.entity.repository.OccupationRepository;
import cz.rodro.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OccupationServiceImpl implements OccupationService {

    private final OccupationRepository occupationRepository;
    private final OccupationMapper occupationMapper;
    private final InstitutionRepository institutionRepository;

    @Override
    @Transactional
    public OccupationDTO createOccupation(OccupationDTO dto) {
        // The mapper automatically handles mapping the personImageUrl field from the DTO to the entity.
        OccupationEntity entity = occupationMapper.toEntity(dto);

        if (dto.getInstitution() == null || dto.getInstitution().getId() == null) {
            throw new IllegalArgumentException("Institution ID must be provided");
        }

        InstitutionEntity institution = institutionRepository.findById(dto.getInstitution().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Institution not found"));

        entity.setInstitution(institution);

        OccupationEntity saved = occupationRepository.save(entity);
        return occupationMapper.toDTO(saved);
    }

    @Override
    public OccupationDTO getOccupationById(Long occupationId) {
        OccupationEntity entity = findOccupationOrThrow(occupationId);
        return occupationMapper.toDTO(entity);
    }

    @Override
    public List<OccupationDTO> getAllOccupations() {
        return occupationRepository.findAll()
                .stream()
                .map(occupationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOccupation(Long occupationId) {
        OccupationEntity entity = findOccupationOrThrow(occupationId);
        occupationRepository.delete(entity);
    }

    private OccupationEntity findOccupationOrThrow(Long id) {
        return occupationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Occupation with ID " + id + " not found."));
    }

    public OccupationDTO getOccupationDetail(Long id) {
        OccupationEntity entity = occupationRepository.findByIdWithPersons(id)
                .orElseThrow(() -> new ResourceNotFoundException("Occupation not found"));
        return occupationMapper.toDTO(entity);
    }
}
