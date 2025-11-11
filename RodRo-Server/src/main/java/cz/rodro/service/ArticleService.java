package cz.rodro.service;

import cz.rodro.dto.ArticleReadDTO;
import cz.rodro.dto.ArticleWriteDTO;
import cz.rodro.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {

    /**
     * Creates a new article. The author is injected by the security context.
     * @param article The input DTO from the request body.
     * @param author The UserEntity of the currently authenticated user.
     * @return The created article mapped to a ReadDTO.
     */
    ArticleReadDTO addArticle(ArticleWriteDTO article, UserEntity author);

    /**
     * Updates an existing article.
     * @param id The ID of the article to edit.
     * @param articleDTO The input DTO containing updated fields.
     * @param currentUser The authenticated user (required for ownership check).
     * @return The updated article mapped to a ReadDTO.
     */
    ArticleReadDTO editArticle(Long id, ArticleWriteDTO articleDTO, UserEntity currentUser);

    /**
     * Deletes an article by ID.
     * @param articleId The ID of the article to delete.
     * @param currentUser The authenticated user (required for ownership check or admin check).
     */
    void delete(Long articleId, UserEntity currentUser);


    /**
     * Gets all articles with dynamic sorting and pagination.
     * @param pageable Contains sort parameters (e.g., sort=views,desc) and pagination data.
     * @return A Page object containing ArticleReadDTOs.
     */
    Page<ArticleReadDTO> getAllArticles(Pageable pageable);

    /**
     * Gets a single article by ID and increments the view counter.
     * @param articleId The ID of the article.
     * @return The detailed ArticleReadDTO.
     */
    ArticleReadDTO getById(Long articleId);

    void incrementViews(Long articleId);
}
