package com.sparta.todo.service;

import com.sparta.todo.dto.TodoRequest;
import com.sparta.todo.dto.TodoResponse;
import com.sparta.todo.entity.Todo;
import com.sparta.todo.entity.User;
import com.sparta.todo.exception.custom.PasswordMismatchException;
import com.sparta.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final UserService userService;
    private final TodoRepository todoRepository;

    public List<TodoResponse> getTodos(int pageNumber, int pageSize) {
        List<Todo> todos = todoRepository.findTodosWithPagination(pageNumber,pageSize);
        return todos.stream()
                .map(TodoResponse::of)
                .collect(Collectors.toList());
    }

    public TodoResponse createTodo(TodoRequest request) {
        User user = userService.getUserEntity(request.getUserId());
        Todo entity = new Todo(request, user);
        return TodoResponse.of(todoRepository.save(entity));
    }

    public TodoResponse getTodo(long todoId) {
        Todo entity = todoRepository.findById(todoId).orElseThrow();
        return TodoResponse.of(entity);
    }

    public List<TodoResponse> getTodos(String date, String managerName, int page, int size) {
        List<Todo> entities = todoRepository.search(date, managerName, page, size);
        return entities.stream()
                .map(TodoResponse::of)
                .collect(Collectors.toList());
    }

    public TodoResponse updateTodo(long todoId, TodoRequest request) {
        Todo entity = todoRepository.findById(todoId).orElseThrow();
        if (!entity.getPassword().equals(request.getPassword())) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }

        if (request.getTodo() != null) {
            entity.changeTodo(request.getTodo());
        }

        User user = userService.getUserEntity(request.getUserId());
        if (user != null) {
            entity.changeUser(user);
        }

        todoRepository.update(entity);

        Todo updatedEntity = todoRepository.findById(todoId).orElseThrow();
        return TodoResponse.of(updatedEntity);
    }

    public void deleteTodo(long todoId, TodoRequest request) {
        Todo entity = todoRepository.findById(todoId).orElseThrow();
        if (!entity.getPassword().equals(request.getPassword())) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }

        todoRepository.delete(todoId);
    }


}