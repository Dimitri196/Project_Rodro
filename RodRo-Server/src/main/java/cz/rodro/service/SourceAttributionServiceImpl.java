package cz.rodro.service;

import cz.rodro.constant.AttributionType;
import cz.rodro.dto.SourceAttributionDTO;
import cz.rodro.dto.mapper.SourceAttributionMapper;
import cz.rodro.entity.SourceEntity;
import cz.rodro.entity.SourceAttributionEntity;
import cz.rodro.entity.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SourceAttributionServiceImpl implements SourceAttributionService {

    private final SourceAttributionRepository sourceAttributionRepository;
    private final SourceRepository sourceRepository;
    private final PersonRepository personRepository;
    private final PersonOccupationRepository personOccupationRepository;
    private final FamilyRepository familyRepository;
    private final PersonMilitaryServiceRepository militaryServiceRepository;

    private final SourceAttributionMapper mapper;

    // --- Fetch all sources for a person ---
    @Override
    @Transactional(readOnly = true)
    public List<SourceAttributionDTO> getByPersonId(Long personId) {
        if (personId == null) throw new IllegalArgumentException("personId must not be null");
        return sourceAttributionRepository.findByPerson_Id(personId)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    // --- Fetch by person and event type (BIRTH, DEATH, etc.) ---
    @Override
    @Transactional(readOnly = true)
    public List<SourceAttributionDTO> getByPersonIdAndEventType(Long personId, String eventType) {
        if (personId == null) throw new IllegalArgumentException("personId must not be null");
        if (!StringUtils.hasText(eventType)) throw new IllegalArgumentException("eventType must not be blank");

        AttributionType type;
        try {
            type = AttributionType.valueOf(eventType.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Unknown AttributionType: " + eventType);
        }

        return sourceAttributionRepository.findByPerson_IdAndType(personId, type)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    // --- Create new source attribution ---
    @Override
    public SourceAttributionDTO create(SourceAttributionDTO dto) {
        if (dto == null) throw new IllegalArgumentException("dto is required");
        if (dto.getSourceId() == null) throw new IllegalArgumentException("sourceId is required");
        if (dto.getType() == null) throw new IllegalArgumentException("type is required");

        SourceEntity source = sourceRepository.findById(dto.getSourceId())
                .orElseThrow(() -> new IllegalArgumentException("Source not found: " + dto.getSourceId()));

        SourceAttributionEntity entity = mapper.toEntity(dto);
        entity.setSource(source);

        // Attach the correct parent entity depending on type
        switch (dto.getType()) {
            case BIRTH:
            case BAPTISM:
            case DEATH:
            case BURIAL:
            case OTHER:
                if (dto.getPersonId() == null)
                    throw new IllegalArgumentException("personId is required for event type " + dto.getType());
                entity.setPerson(personRepository.findById(dto.getPersonId())
                        .orElseThrow(() -> new IllegalArgumentException("Person not found: " + dto.getPersonId())));
                break;

            case OCCUPATION:
                if (dto.getOccupationId() == null)
                    throw new IllegalArgumentException("occupationId is required for OCCUPATION attribution");
                entity.setOccupation(personOccupationRepository.findById(dto.getOccupationId())
                        .orElseThrow(() -> new IllegalArgumentException("Occupation not found: " + dto.getOccupationId())));
                break;

            case FAMILY:
                if (dto.getFamilyId() == null)
                    throw new IllegalArgumentException("familyId is required for FAMILY attribution");
                entity.setFamily(familyRepository.findById(dto.getFamilyId())
                        .orElseThrow(() -> new IllegalArgumentException("Family not found: " + dto.getFamilyId())));
                break;

            case MILITARY:
                if (dto.getMilitaryServiceId() == null)
                    throw new IllegalArgumentException("militaryServiceId is required for MILITARY attribution");
                entity.setMilitaryService(militaryServiceRepository.findById(dto.getMilitaryServiceId())
                        .orElseThrow(() -> new IllegalArgumentException("Military service not found: " + dto.getMilitaryServiceId())));
                break;
        }

        SourceAttributionEntity saved = sourceAttributionRepository.save(entity);
        return mapper.toDTO(saved);
    }

    // --- Delete attribution ---
    @Override
    public void delete(Long id) {
        if (id == null) throw new IllegalArgumentException("id must not be null");
        if (!sourceAttributionRepository.existsById(id))
            throw new IllegalArgumentException("Source attribution not found: " + id);
        sourceAttributionRepository.deleteById(id);
    }

    @Override
    public List<SourceAttributionDTO> getByPersonIdAllTargets(Long personId) {
        List<SourceAttributionEntity> entities = sourceAttributionRepository.findAllByPersonIdOrLinkedEntities(personId);
        return entities.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}