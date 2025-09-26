package cz.rodro.dto.mapper;

import cz.rodro.dto.OccupationDTO;
import cz.rodro.entity.OccupationEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {PersonOccupationMapper.class, PersonMapper.class})
public interface OccupationMapper {

    @Mapping(target = "institution.occupations", ignore = true)
    @Mapping(source = "personImageUrl", target = "personImageUrl")
    OccupationDTO toDTO(OccupationEntity entity);

    @Mapping(target = "institution", source = "institution")
    @Mapping(source = "personImageUrl", target = "personImageUrl")
    OccupationEntity toEntity(OccupationDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "institution", ignore = true)
    @Mapping(source = "personImageUrl", target = "personImageUrl")
    void updateOccupationEntity(OccupationDTO dto, @MappingTarget OccupationEntity entity);

}
