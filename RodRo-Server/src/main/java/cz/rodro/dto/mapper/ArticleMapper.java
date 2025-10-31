package cz.rodro.dto.mapper;

import cz.rodro.entity.ArticleEntity;
import cz.rodro.dto.ArticleReadDTO; // The DTO for output/display
import cz.rodro.dto.ArticleWriteDTO; // The DTO for input/create/update
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    // ----------------------------------------------------------------------
    // 1. MAPPING FOR READ OPERATIONS (Entity -> DTO)
    // ----------------------------------------------------------------------

    /**
     * Converts the database Entity to the DTO used for Index and Detail views.
     * Maps the author's full name from the linked UserEntity to the DTO's authorName field.
     * Maps the ArticleEntity's 'createdAt' to the DTO's 'createdAt'.
     */
    @Mapping(source = "id", target = "id") // Map 'id' to '_id' for frontend
    @Mapping(source = "author.fullName", target = "authorName") // Map UserEntity.fullName to ArticleReadDTO.authorName
    @Mapping(source = "createdAt", target = "createdAt") // Map the creation timestamp
    ArticleReadDTO toReadDTO(ArticleEntity source);


    // ----------------------------------------------------------------------
    // 2. MAPPING FOR WRITE OPERATIONS (DTO -> Entity)
    // ----------------------------------------------------------------------

    /**
     * Converts the incoming DTO (which only contains title, desc, content, categories)
     * into a new Entity object. The 'author' field MUST be set separately in the service layer.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", ignore = true) // Ignore the author; it's set by the service layer (SecurityContext)
    @Mapping(target = "views", ignore = true)  // Ignore views; it defaults to 0 in the entity's @PrePersist
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ArticleEntity toEntity(ArticleWriteDTO source);


    // ----------------------------------------------------------------------
    // 3. MAPPING FOR UPDATE OPERATIONS (DTO -> Existing Entity)
    // ----------------------------------------------------------------------

    /**
     * Updates an existing Entity with values from the incoming DTO.
     */
    @Mapping(target = "id", ignore = true) // Never update the ID
    @Mapping(target = "author", ignore = true) // Never change the original author
    @Mapping(target = "views", ignore = true)  // Views is updated separately (e.g., via a counter method)
    @Mapping(target = "createdAt", ignore = true) // Never overwrite the creation date
    @Mapping(target = "updatedAt", ignore = true) // Let the @PreUpdate handle this
    void updateArticleEntity(ArticleWriteDTO source, @MappingTarget ArticleEntity target);
}