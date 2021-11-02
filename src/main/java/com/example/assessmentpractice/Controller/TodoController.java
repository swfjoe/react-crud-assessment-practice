package com.example.assessmentpractice.Controller;

import com.example.assessmentpractice.Model.TodoItem;
import com.example.assessmentpractice.Repository.TodoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
public class TodoController {
    TodoRepository newTodoRepository;

    public TodoController(TodoRepository newTodoRepository) {
        this.newTodoRepository = newTodoRepository;
    }

    @GetMapping("/api/items")
    public Iterable<TodoItem> getAllItems() {
        return this.newTodoRepository.findAll();
    }

    @PostMapping("/api/items")
    public TodoItem createNewTodoItem(@RequestBody TodoItem newTodoItem) {
        return this.newTodoRepository.save(newTodoItem);
    }

    @PatchMapping("/api/items/{id}")
    public TodoItem modifyTodoItem(@PathVariable Long id, @RequestBody Map<String, Object> updateMap) {
        TodoItem updateItem = this.newTodoRepository.findById(id).get();
        updateMap.forEach((key, value) -> {
            switch (key) {
                case "content" -> updateItem.setContent((String) value);
                case "completed" -> updateItem.setCompleted((Boolean) value);
            }
        });
        return this.newTodoRepository.save(updateItem);
    }

    @DeleteMapping("/api/items/{id}")
    public TodoItem deleteTodoItem(@PathVariable Long id) {
        TodoItem toDelete = this.newTodoRepository.findById(id).get();
        this.newTodoRepository.deleteById(id);
        return toDelete;
    }

}