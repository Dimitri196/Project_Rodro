package cz.rodro.controller;

import cz.rodro.dto.ArticleDTO;
import cz.rodro.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/articles")
    @ResponseStatus(HttpStatus.CREATED)
    public ArticleDTO createArticle(@Valid @RequestBody ArticleDTO articleDTO) {
        return articleService.addArticle(articleDTO);
    }

    @GetMapping("/articles")
    public List<ArticleDTO> getAllArticles() {
        return articleService.getAll();
    }

    @GetMapping("/articles/{id}")
    public ArticleDTO getArticleById(@PathVariable Long id) {
        return articleService.getById(id);
    }

    @PutMapping("/articles/{id}")
    public ArticleDTO updateArticle(
            @PathVariable Long id,
            @Valid @RequestBody ArticleDTO articleDTO) {
        return articleService.editArticle(id, articleDTO);
    }

    @DeleteMapping("/articles/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArticle(@PathVariable Long id) {
        articleService.delete(id);
    }
}
