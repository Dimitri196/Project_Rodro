package cz.rodro.service;

import cz.rodro.dto.SubdivisionDTO;

import java.util.List;

public interface SubdivisionService {
    /**
     * Retrieves all subdivisions.
     *
     * @return a list of SubdivisionDTO objects representing all subdivisions
     */
    List<SubdivisionDTO> getAll();
    SubdivisionDTO getById(Long id);
    SubdivisionDTO create(SubdivisionDTO dto);
    SubdivisionDTO update(Long id, SubdivisionDTO dto);
    void delete(Long id);

}