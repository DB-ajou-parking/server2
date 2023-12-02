package com.example.ajouparking.repository;

import com.example.ajouparking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    boolean existsByUsername(String username);

    User findByUsername(String username);
}
