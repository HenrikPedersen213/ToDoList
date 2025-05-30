package com.ToDoList.demo.config;

import com.ToDoList.demo.model.Todo;
import com.ToDoList.demo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Configuration class to load initial data for development and testing.
 */
@Configuration
@RequiredArgsConstructor
public class DataLoader {

    private final TodoRepository todoRepository;

    /**
     * CommandLineRunner bean to load sample data when the application starts.
     * Only active in "dev" profile to avoid loading test data in production.
     */
    @Bean
    @Profile("!prod")
    public CommandLineRunner loadData() {
        return args -> {
            // Create sample todos
            Todo todo1 = new Todo("Complete Spring Boot project", "Implement all required features for the To-Do List application");
            Todo todo2 = new Todo("Learn Docker", "Study Docker basics and containerize the application");
            Todo todo3 = new Todo("Setup CI/CD pipeline", "Configure Jenkins or GitHub Actions for continuous integration");
            
            // Mark one as completed
            todo2.markCompleted();
            
            // Set creation dates to have different timestamps
            todo1.setCreatedAt(LocalDateTime.now().minusDays(2));
            todo2.setCreatedAt(LocalDateTime.now().minusDays(5));
            todo3.setCreatedAt(LocalDateTime.now().minusHours(12));
            
            // Save all todos
            todoRepository.saveAll(Arrays.asList(todo1, todo2, todo3));
            
            System.out.println("Sample data loaded successfully!");
        };
    }
}