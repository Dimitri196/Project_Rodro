package cz.rodro.controller; // Assuming the package structure

import cz.rodro.dto.ArticleReadDTO;
import cz.rodro.dto.ArticleWriteDTO;
import cz.rodro.entity.UserEntity; // Import the custom UserEntity
import cz.rodro.entity.repository.UserRepository;
import cz.rodro.exception.AccessDeniedException;
import cz.rodro.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final UserRepository userRepository;

    // ------------------------------------------------------------------------
    // CREATE
    // ------------------------------------------------------------------------
    @PostMapping
    public ResponseEntity<ArticleReadDTO> addArticle(
            @Valid @RequestBody ArticleWriteDTO articleDTO,
            Authentication authentication) {

        if (authentication == null || authentication.getName() == null) {
            throw new AccessDeniedException("User must be logged in to create an article.");
        }

        // ✅ Use email-based lookup
        UserEntity author = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new NoSuchElementException("User not found: " + authentication.getName()));

        ArticleReadDTO saved = articleService.addArticle(articleDTO, author);
        return ResponseEntity.ok(saved);
    }

    // ------------------------------------------------------------------------
    // UPDATE
    // ------------------------------------------------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<ArticleReadDTO> editArticle(
            @PathVariable Long id,
            @Valid @RequestBody ArticleWriteDTO articleDTO,
            Authentication authentication) {

        if (authentication == null || authentication.getName() == null) {
            throw new AccessDeniedException("User must be logged in to edit an article.");
        }

        UserEntity currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new NoSuchElementException("User not found: " + authentication.getName()));

        ArticleReadDTO updated = articleService.editArticle(id, articleDTO, currentUser);
        return ResponseEntity.ok(updated);
    }

    // ------------------------------------------------------------------------
    // DELETE
    // ------------------------------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(
            @PathVariable Long id,
            Authentication authentication) {

        if (authentication == null || authentication.getName() == null) {
            throw new AccessDeniedException("User must be logged in to delete an article.");
        }

        UserEntity currentUser = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new NoSuchElementException("User not found: " + authentication.getName()));

        articleService.delete(id, currentUser);
        return ResponseEntity.noContent().build();
    }

    // ------------------------------------------------------------------------
    // READ ALL
    // ------------------------------------------------------------------------
    @GetMapping
    public ResponseEntity<Page<ArticleReadDTO>> getAllArticles(Pageable pageable) {
        Page<ArticleReadDTO> page = articleService.getAllArticles(pageable);
        return ResponseEntity.ok(page);
    }

    // ------------------------------------------------------------------------
    // READ ONE (increments views)
    // ------------------------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<ArticleReadDTO> getArticleById(@PathVariable Long id) {
        ArticleReadDTO article = articleService.getById(id);
        return ResponseEntity.ok(article);
    }
}