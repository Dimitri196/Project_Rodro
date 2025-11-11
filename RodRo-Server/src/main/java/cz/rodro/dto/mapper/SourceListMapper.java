package cz.rodro.dto.mapper;

import cz.rodro.constant.ConfessionType;
import cz.rodro.constant.SourceType;
import cz.rodro.dto.SourceListDTO;
import cz.rodro.dto.SourceListProjection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SourceListMapper {

    /**
     * Maps a SourceListProjection to a frontend-friendly SourceListDTO.
     * MapStruct will automatically use mapSourceTypeToString() for the 'type' field.
     */
    SourceListDTO toDTO(SourceListProjection projection);

    /**
     * Custom mapping method for converting the SourceType enum to its display name String.
     * This is automatically used by toDTO() for the 'type' field mapping.
     */
    default String mapSourceTypeToString(SourceType sourceType) {
        return sourceType != null ? sourceType.getDisplayName() : null;
    }

    /**
     * Custom mapping method for converting the ConfessionType enum to its display name String.
     * This is automatically used by toDTO() for the 'confession' field mapping.
     */
    default String mapConfessionTypeToString(ConfessionType confessionType) {
        return confessionType != null ? confessionType.getDisplayName() : null;
    }
}