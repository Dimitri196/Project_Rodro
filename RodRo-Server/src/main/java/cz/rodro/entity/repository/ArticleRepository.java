package cz.rodro.entity.repository;

import cz.rodro.entity.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    // ----------------------------------------------------------------
    // 1. DYNAMIC FETCHING WITH SORTING (Essential for ArticleIndex)
    // ----------------------------------------------------------------

    /**
     * Retrieves a page of all articles.
     * The Pageable argument allows dynamic sorting (by title, createdAt, views)
     * and pagination, driven directly by the frontend request parameters.
     * This method directly replaces the simple findAll() used in the index view.
     */
    Page<ArticleEntity> findAll(Pageable pageable);


    // ----------------------------------------------------------------
    // 2. SEARCH AND FILTERING (Enhancement for future search bar)
    // ----------------------------------------------------------------

    /**
     * Custom query to find articles that contain a specific category (tag).
     * This uses the '@Query' annotation to search within the 'article_categories' table
     * created by the @ElementCollection mapping.
     *
     * @param category The tag to search for (e.g., "Infantry").
     * @param pageable Sorting and pagination information.
     * @return A page of filtered articles.
     */
    @Query("SELECT a FROM ArticleEntity a JOIN a.categories c WHERE c = :category")
    Page<ArticleEntity> findByCategory(@Param("category") String category, Pageable pageable);


    /**
     * Finds articles where the title or description contains a given keyword (case-insensitive).
     * This is useful for implementing a general search function.
     */
    Page<ArticleEntity> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String titleKeyword, String descriptionKeyword, Pageable pageable);

}
