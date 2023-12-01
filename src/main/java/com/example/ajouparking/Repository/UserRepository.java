package com.example.ajouparking.Repository;

import com.example.ajouparking.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByUsername(String username);

    User findByUsername(String username);
}
