package com.sparta.todo.controller;

import com.sparta.todo.dto.TodoRequest;
import com.sparta.todo.dto.TodoResponse;
import com.sparta.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PostMapping("/api/v1/todos")
    public ResponseEntity<TodoResponse> createTodo(@RequestBody TodoRequest request) {
        TodoResponse response = todoService.createTodo(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/v1/todos/{todoId}")
    public ResponseEntity<TodoResponse> getTodo(@PathVariable long todoId) {
        TodoResponse response = todoService.getTodo(todoId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/v1/todos")
    public ResponseEntity<List<TodoResponse>> getTodos(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String managerName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        List<TodoResponse> response = todoService.getTodos(date, managerName, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/v1/todos")
    public List<TodoResponse> getTodos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
       return todoService.getTodos(page, size);
    }


    @PutMapping("/api/v1/todos/{todoId}")
    public ResponseEntity<TodoResponse> updateTodo(
            @PathVariable long todoId,
            @RequestBody TodoRequest request
    ) {
        TodoResponse response = todoService.updateTodo(todoId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/api/v1/todos/{todoId}")
    public void deleteTodo(
            @PathVariable long todoId,
            @RequestBody TodoRequest request
    ) {
        todoService.deleteTodo(todoId, request);
    }
}