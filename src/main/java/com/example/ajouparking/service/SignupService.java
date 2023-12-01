package com.example.ajouparking.service;

import com.example.ajouparking.dto.SignupRequestDto;
import com.example.ajouparking.entity.User;
import com.example.ajouparking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignupService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public void signup(SignupRequestDto signupRequestDto){

        boolean isUserExists = userRepository.existsByUsername(signupRequestDto.getUsername());
        if(isUserExists){
            return;
        }

        User userEntity = new User();

        userEntity.setUsername(signupRequestDto.getUsername());
        userEntity.setPassword(bCryptPasswordEncoder.encode(signupRequestDto.getPassword()));
        userEntity.setRole("ROLE_USER");

        userRepository.save(userEntity);
    }
}
