package com.sparta.todo.dto;

import com.sparta.todo.entity.Todo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class TodoResponse {
    private final long id;
    private final String todo;
    private final long userId;
    private final String userName;
    private final String userEmail;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public static TodoResponse of(Todo entity) {
        return new TodoResponse(
                entity.getId(),
                entity.getTodo(),
                entity.getUser().getId(),
                entity.getUser().getUsername(),
                entity.getUser().getUsername(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}