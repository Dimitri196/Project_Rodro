package cz.rodro.service;

import cz.rodro.dto.ArticleDTO;
import cz.rodro.dto.mapper.ArticleMapper;
import cz.rodro.entity.ArticleEntity;
import cz.rodro.entity.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.StreamSupport;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public ArticleDTO addArticle(ArticleDTO article) {
        ArticleEntity newArticle = articleMapper.toEntity(article);
        articleRepository.save(newArticle);
        return articleMapper.toDTO(newArticle);
    }

    @Override
    public List<ArticleDTO> getAll() {
        return StreamSupport.stream(articleRepository.findAll().spliterator(), false)
                .map(articleMapper::toDTO)
                .toList();
    }

    @Override
    public ArticleDTO getById(Long articleId) {
        ArticleEntity fetchedArticle = articleRepository
                .findById(articleId)
                .orElseThrow(() -> new NoSuchElementException("Article not found with id: " + articleId));
        return articleMapper.toDTO(fetchedArticle);
    }

    @Override
    public ArticleDTO editArticle(Long id, ArticleDTO articleDTO) {
        ArticleEntity existing = getArticleOrThrow(id);
        articleMapper.updateArticleEntity(articleDTO, existing);
        articleRepository.save(existing);
        return articleMapper.toDTO(existing);
    }

    @Override
    public void delete(Long articleId) {
        if (!articleRepository.existsById(articleId)) {
            throw new NoSuchElementException("Article not found with id: " + articleId);
        }
        articleRepository.deleteById(articleId);
    }

    private ArticleEntity getArticleOrThrow(Long articleId) {
        return articleRepository
                .findById(articleId)
                .orElseThrow(() -> new NoSuchElementException("Article not found with id: " + articleId));
    }
}
