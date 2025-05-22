package cz.rodro.service;

import cz.rodro.dto.SourceDTO;

import java.util.List;

public interface SourceService {

    SourceDTO addSource(SourceDTO sourceDTO);
    SourceDTO getSource(Long id);
    List<SourceDTO> getAllSources();
    SourceDTO updateSource(Long id, SourceDTO sourceDTO);
    void deleteSource(Long id);
}
