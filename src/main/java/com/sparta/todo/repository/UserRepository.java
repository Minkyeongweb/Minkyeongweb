package com.sparta.todo.repository;

import com.sparta.todo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 사용자 정의 메서드가 필요한 경우 여기에 추가할 수 있습니다.
}
