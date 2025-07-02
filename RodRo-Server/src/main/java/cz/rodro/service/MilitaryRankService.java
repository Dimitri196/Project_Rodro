package cz.rodro.service;

import cz.rodro.dto.MilitaryRankDTO;
import cz.rodro.entity.MilitaryRankEntity;

import java.util.List;

public interface MilitaryRankService {

    List<MilitaryRankDTO> getAll();

    MilitaryRankDTO getMilitaryRank(Long rankId);

    MilitaryRankDTO addMilitaryRank(MilitaryRankDTO militaryRankDTO);

    MilitaryRankDTO removeMilitaryRank(Long rankId);

    MilitaryRankDTO updateMilitaryRank(Long rankId, MilitaryRankDTO militaryRankDTO);

    MilitaryRankEntity fetchMilitaryRankById(Long id, String type);

    MilitaryRankDTO getMilitaryRankWithPersons(Long id);

}
