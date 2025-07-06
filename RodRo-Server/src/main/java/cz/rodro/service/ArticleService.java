package cz.rodro.service;

import cz.rodro.dto.ArticleDTO;

import java.util.List;

public interface ArticleService {

    ArticleDTO addArticle(ArticleDTO article);
    List<ArticleDTO> getAll();
    ArticleDTO getById(Long articleId);
    ArticleDTO editArticle(Long id, ArticleDTO article); // fixed signature and return type
    void delete(Long articleId);
}