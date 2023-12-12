package com.example.ajouparking.service;

import com.example.ajouparking.entity.User;
import com.example.ajouparking.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserById(long userId) {
        return userRepository.findById(userId).orElse(null);
    }

}