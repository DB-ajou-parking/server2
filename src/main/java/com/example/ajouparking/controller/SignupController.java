package com.example.ajouparking.controller;

import com.example.ajouparking.dto.SignupRequestDto;
import com.example.ajouparking.service.SignupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


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
