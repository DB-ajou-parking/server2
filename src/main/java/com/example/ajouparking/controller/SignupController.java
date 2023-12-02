package com.example.ajouparking.controller;

import com.example.ajouparking.dto.SignupRequestDto;
import com.example.ajouparking.service.SignupService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@Controller
@RequiredArgsConstructor
public class SignupController {

    private final SignupService signupService;

    @GetMapping("/auth/signup")
    public String signup(){
        return "signup";
    }

//    @PostMapping("/auth/signup")
//    public ResponseEntity<?> signup(SignupRequestDto signupRequestDto){
//        URI selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest() .toUriString());
//        signupService.signup(signupRequestDto);
//        return ResponseEntity.created(selfLink).build();
//    }
    @PostMapping("/auth/signup")
    public void signup(SignupRequestDto signupRequestDto){
        signupService.signup(signupRequestDto);
    }
}
