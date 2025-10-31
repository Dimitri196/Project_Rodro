package cz.rodro.dto.mapper;

import cz.rodro.dto.ArticleReadDTO;
import cz.rodro.dto.ArticleWriteDTO;
import cz.rodro.entity.ArticleEntity;
import cz.rodro.entity.UserEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
public class ArticleMapperImpl implements ArticleMapper {

    @Override
    public ArticleReadDTO toReadDTO(ArticleEntity source) {
        if ( source == null ) {
            return null;
        }

        ArticleReadDTO articleReadDTO = new ArticleReadDTO();

        articleReadDTO.setId( source.getId() );
        articleReadDTO.setAuthorName( sourceAuthorFullName( source ) );
        articleReadDTO.setCreatedAt( source.getCreatedAt() );
        articleReadDTO.setTitle( source.getTitle() );
        articleReadDTO.setDescription( source.getDescription() );
        articleReadDTO.setViews( source.getViews() );
        List<String> list = source.getCategories();
        if ( list != null ) {
            articleReadDTO.setCategories( new ArrayList<String>( list ) );
        }
        articleReadDTO.setContent( source.getContent() );

        return articleReadDTO;
    }

    @Override
    public ArticleEntity toEntity(ArticleWriteDTO source) {
        if ( source == null ) {
            return null;
        }

        ArticleEntity articleEntity = new ArticleEntity();

        articleEntity.setTitle( source.getTitle() );
        articleEntity.setDescription( source.getDescription() );
        List<String> list = source.getCategories();
        if ( list != null ) {
            articleEntity.setCategories( new ArrayList<String>( list ) );
        }
        articleEntity.setContent( source.getContent() );

        return articleEntity;
    }

    @Override
    public void updateArticleEntity(ArticleWriteDTO source, ArticleEntity target) {
        if ( source == null ) {
            return;
        }

        target.setTitle( source.getTitle() );
        target.setDescription( source.getDescription() );
        if ( target.getCategories() != null ) {
            List<String> list = source.getCategories();
            if ( list != null ) {
                target.getCategories().clear();
                target.getCategories().addAll( list );
            }
            else {
                target.setCategories( null );
            }
        }
        else {
            List<String> list = source.getCategories();
            if ( list != null ) {
                target.setCategories( new ArrayList<String>( list ) );
            }
        }
        target.setContent( source.getContent() );
    }

    private String sourceAuthorFullName(ArticleEntity articleEntity) {
        if ( articleEntity == null ) {
            return null;
        }
        UserEntity author = articleEntity.getAuthor();
        if ( author == null ) {
            return null;
        }
        String fullName = author.getFullName();
        if ( fullName == null ) {
            return null;
        }
        return fullName;
    }
}
