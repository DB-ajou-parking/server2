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

        User user = User.builder()
                .username(signupRequestDto.getUsername())
                .password(bCryptPasswordEncoder.encode(signupRequestDto.getPassword()))
                .role("ROLE_USER")
                .build();

        userRepository.save(user);
    }
}
