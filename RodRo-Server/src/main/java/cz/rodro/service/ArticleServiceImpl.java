package cz.rodro.service;

import cz.rodro.dto.ArticleReadDTO;
import cz.rodro.dto.ArticleWriteDTO;
import cz.rodro.dto.mapper.ArticleMapper;
import cz.rodro.entity.ArticleEntity;
import cz.rodro.entity.UserEntity;
import cz.rodro.entity.repository.ArticleRepository;
import cz.rodro.exception.AccessDeniedException; // Assuming you have an exception for security
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    // Use constructor injection instead of @Autowired field injection
    public ArticleServiceImpl(ArticleRepository articleRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
    }

    // ----------------------------------------------------------------
    // WRITE OPERATIONS (CRUD)
    // ----------------------------------------------------------------

    @Override
    @Transactional
    public ArticleReadDTO addArticle(ArticleWriteDTO articleDTO, UserEntity author) {
        ArticleEntity newArticle = articleMapper.toEntity(articleDTO);
        newArticle.setAuthor(author); // ✅ critical line
        newArticle.setViews(0L);
        articleRepository.save(newArticle);
        return articleMapper.toReadDTO(newArticle);
    }

    @Override
    @Transactional
    public ArticleReadDTO editArticle(Long id, ArticleWriteDTO articleDTO, UserEntity currentUser) {
        ArticleEntity existing = getArticleOrThrow(id);

        // Security Check: Only the author or an admin can edit the article
        if (currentUser.getUserId() != existing.getAuthor().getUserId() && !currentUser.isAdmin()) {
            throw new AccessDeniedException("User is not the author and does not have administrative rights to edit this paper.");
        }

        // 1. Map DTO to Entity (Mapper updates mutable fields)
        articleMapper.updateArticleEntity(articleDTO, existing);

        // 2. Save (PreUpdate will update 'updatedAt')
        articleRepository.save(existing);
        return articleMapper.toReadDTO(existing);
    }

    @Override
    @Transactional
    public void delete(Long articleId, UserEntity currentUser) {
        ArticleEntity articleToDelete = getArticleOrThrow(articleId);

        // Security Check: Only the author or an admin can delete the article
        if (currentUser.getUserId() != articleToDelete.getAuthor().getUserId() && !currentUser.isAdmin()) {
            throw new AccessDeniedException("User is not the author and does not have administrative rights to delete this paper.");
        }

        articleRepository.delete(articleToDelete);
    }

    // ----------------------------------------------------------------
    // READ OPERATIONS (INDEX, DETAIL, VIEWS)
    // ----------------------------------------------------------------

    @Override
    public Page<ArticleReadDTO> getAllArticles(Pageable pageable) {
        // Use the findAll(Pageable) method from the improved Repository for sorting/pagination
        Page<ArticleEntity> articlePage = articleRepository.findAll(pageable);

        // Map the Page of Entities to a Page of ReadDTOs
        return articlePage.map(articleMapper::toReadDTO);
    }

    @Override
    @Transactional // Transactional needed to ensure view update and fetch are consistent
    public ArticleReadDTO getById(Long articleId) {
        ArticleEntity fetchedArticle = getArticleOrThrow(articleId);

        // Increment views on read (essential feature of the detail page)
        incrementViews(articleId);

        // Return the DTO
        return articleMapper.toReadDTO(fetchedArticle);
    }

    @Override
    @Transactional
    public void incrementViews(Long articleId) {
        // Find the article, update the view count, and save.
        ArticleEntity article = getArticleOrThrow(articleId);
        article.setViews(article.getViews() + 1);
        articleRepository.save(article);
    }

    // ----------------------------------------------------------------
    // UTILITY
    // ----------------------------------------------------------------

    private ArticleEntity getArticleOrThrow(Long articleId) {
        return articleRepository
                .findById(articleId)
                .orElseThrow(() -> new NoSuchElementException("Research paper not found with ID: " + articleId));
    }
}
