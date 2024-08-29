package com.sparta.todo.entity;

import com.sparta.todo.dto.TodoRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Todo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String todo;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setTodo(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setTodo(null);
    }
    

    public Todo(TodoRequest request, User user) {
        this.todo = request.getTodo();
        this.user = user;
        this.password = request.getPassword();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Todo(long id, String todo, User user, String password, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.todo = todo;
        this.user = user;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void changeId(long id) {
        this.id = id;
    }

    public void changeTodo(String todo) {
        this.todo = todo;
        this.updatedAt = LocalDateTime.now();
    }

    public void changeUser(User user) {
        this.user = user;
        this.updatedAt = LocalDateTime.now();
    }

    public void ChangeUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


    public void updateTodoContent(Todo todo, String newTodoContent) {
        todo.changeTodo(newTodoContent);
        todo.ChangeUpdatedAt(LocalDateTime.now());
    }

}