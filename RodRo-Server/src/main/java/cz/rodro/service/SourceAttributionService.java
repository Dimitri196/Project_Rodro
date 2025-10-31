package cz.rodro.service;

import cz.rodro.dto.SourceAttributionDTO;

import java.util.List;

public interface SourceAttributionService {

    List<SourceAttributionDTO> getByPersonId(Long personId);

    List<SourceAttributionDTO> getByPersonIdAndEventType(Long personId, String eventType);

    List<SourceAttributionDTO> getByPersonIdAllTargets(Long personId);

    SourceAttributionDTO create(SourceAttributionDTO dto);

    void delete(Long id);
}