package com.ToDoList.demo.repository;

import com.ToDoList.demo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Todo entity providing CRUD operations and custom queries.
 */
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    /**
     * Find all todos ordered by creation date (newest first)
     */
    List<Todo> findAllByOrderByCreatedAtDesc();

    /**
     * Find todos by completion status
     */
    List<Todo> findByCompletedOrderByCreatedAtDesc(boolean completed);

    /**
     * Find todos containing the given title (case insensitive)
     */
    List<Todo> findByTitleContainingIgnoreCaseOrderByCreatedAtDesc(String title);

    /**
     * Find todos containing the given description (case insensitive)
     */
    List<Todo> findByDescriptionContainingIgnoreCaseOrderByCreatedAtDesc(String description);

    /**
     * Custom query to search in both title and description
     */
    @Query("SELECT t FROM Todo t WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(t.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) ORDER BY t.createdAt DESC")
    List<Todo> searchByTitleOrDescription(String searchTerm);
}