package com.ToDoList.demo.controller;

import com.ToDoList.demo.dto.TodoDTO;
import com.ToDoList.demo.dto.TodoRequest;
import com.ToDoList.demo.model.Todo;
import com.ToDoList.demo.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for Todo operations.
 */
@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    /**
     * Get all todos or filter by completion status
     */
    @GetMapping
    public ResponseEntity<List<TodoDTO>> getTodos(
            @RequestParam(required = false) Boolean completed,
            @RequestParam(required = false) String search) {

        List<Todo> todos;
        if (search != null && !search.isEmpty()) {
            todos = todoService.searchTodos(search);
        } else if (completed != null) {
            todos = todoService.getTodosByStatus(completed);
        } else {
            todos = todoService.getAllTodos();
        }

        List<TodoDTO> todoDTOs = todos.stream()
                .map(TodoDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(todoDTOs);
    }

    /**
     * Get a todo by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<TodoDTO> getTodoById(@PathVariable Long id) {
        Todo todo = todoService.getTodoById(id);
        return ResponseEntity.ok(TodoDTO.fromEntity(todo));
    }

    /**
     * Create a new todo
     */
    @PostMapping
    public ResponseEntity<TodoDTO> createTodo(@Valid @RequestBody TodoRequest request) {
        Todo todo = new Todo(request.getTitle(), request.getDescription());
        Todo createdTodo = todoService.createTodo(todo);
        return new ResponseEntity<>(TodoDTO.fromEntity(createdTodo), HttpStatus.CREATED);
    }

    /**
     * Update an existing todo
     */
    @PutMapping("/{id}")
    public ResponseEntity<TodoDTO> updateTodo(@PathVariable Long id, @Valid @RequestBody TodoRequest request) {
        Todo todo = todoService.updateTodo(id, new Todo(request.getTitle(), request.getDescription()));
        return ResponseEntity.ok(TodoDTO.fromEntity(todo));
    }

    /**
     * Mark a todo as completed
     */
    @PatchMapping("/{id}/complete")
    public ResponseEntity<TodoDTO> markTodoAsCompleted(@PathVariable Long id) {
        Todo todo = todoService.markTodoAsCompleted(id);
        return ResponseEntity.ok(TodoDTO.fromEntity(todo));
    }

    /**
     * Delete a todo
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }
}
