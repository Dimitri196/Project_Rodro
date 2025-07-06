package cz.rodro.dto.mapper;

import cz.rodro.dto.ArticleDTO;
import cz.rodro.entity.ArticleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    ArticleEntity toEntity(ArticleDTO source);

    ArticleDTO toDTO(ArticleEntity source);

    @Mapping(target = "id", ignore = true)
    void updateArticleEntity(ArticleDTO source, @MappingTarget ArticleEntity target);
}
