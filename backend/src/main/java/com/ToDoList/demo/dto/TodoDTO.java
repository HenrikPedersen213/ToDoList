package com.ToDoList.demo.dto;

import com.ToDoList.demo.model.Todo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Todo entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {
    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime completedAt;

    /**
     * Convert Todo entity to TodoDTO
     */
    public static TodoDTO fromEntity(Todo todo) {
        TodoDTO dto = new TodoDTO();
        dto.setId(todo.getId());
        dto.setTitle(todo.getTitle());
        dto.setDescription(todo.getDescription());
        dto.setCompleted(todo.isCompleted());
        dto.setCreatedAt(todo.getCreatedAt());
        dto.setUpdatedAt(todo.getUpdatedAt());
        dto.setCompletedAt(todo.getCompletedAt());
        return dto;
    }
}