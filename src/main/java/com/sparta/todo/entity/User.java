package com.sparta.todo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class User {

    // Getter 및 Setter
    @Id @GeneratedValue
    private Long id;
    private String username;
    private String password;  // 비밀번호 필드 추가

    // 기본 생성자
    public User(long userId, String userName, String userEmail, LocalDateTime userCreatedAt, LocalDateTime userUpdatedAt) {
    }

    // 생성자
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

