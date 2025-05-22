package cz.rodro.service;

import cz.rodro.dto.ParishDTO;

import java.util.List;

public interface ParishService {

    ParishDTO addParish(ParishDTO parishDTO);
    ParishDTO getParish(long parishId);
    ParishDTO updateParish(Long parishId, ParishDTO parishDTO);
    void removeParish(long parishId);
    List<ParishDTO> getAllParishes();

}
