package com.ToDoList.demo.service;

import com.ToDoList.demo.model.Todo;
import com.ToDoList.demo.repository.TodoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for managing Todo operations.
 */
@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    /**
     * Get all todos ordered by creation date (newest first)
     */
    public List<Todo> getAllTodos() {
        return todoRepository.findAllByOrderByCreatedAtDesc();
    }

    /**
     * Get todos by completion status
     */
    public List<Todo> getTodosByStatus(boolean completed) {
        return todoRepository.findByCompletedOrderByCreatedAtDesc(completed);
    }

    /**
     * Get a todo by its ID
     */
    public Todo getTodoById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Todo not found with id: " + id));
    }

    /**
     * Create a new todo
     */
    @Transactional
    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    /**
     * Update an existing todo
     */
    @Transactional
    public Todo updateTodo(Long id, Todo todoDetails) {
        Todo todo = getTodoById(id);
        todo.update(todoDetails.getTitle(), todoDetails.getDescription());
        return todoRepository.save(todo);
    }

    /**
     * Mark a todo as completed
     */
    @Transactional
    public Todo markTodoAsCompleted(Long id) {
        Todo todo = getTodoById(id);
        todo.markCompleted();
        return todoRepository.save(todo);
    }

    /**
     * Delete a todo
     */
    @Transactional
    public void deleteTodo(Long id) {
        Todo todo = getTodoById(id);
        todoRepository.delete(todo);
    }

    /**
     * Search todos by title or description
     */
    public List<Todo> searchTodos(String searchTerm) {
        return todoRepository.searchByTitleOrDescription(searchTerm);
    }
}