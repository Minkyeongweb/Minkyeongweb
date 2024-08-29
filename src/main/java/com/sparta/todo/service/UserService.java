package com.sparta.todo.service;

import com.sparta.todo.config.PasswordEncoder;
import com.sparta.todo.entity.User;
import com.sparta.todo.repository.UserRepository;
import com.sparta.todo.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    public User getUserEntity(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public String registerUser(String username, String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(username, encodedPassword);
        userRepository.save(user);

        return jwtTokenProvider.createToken(user.getUsername());
    }
}
