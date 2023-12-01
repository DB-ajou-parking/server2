package com.example.ajouparking.Controller;

import com.example.ajouparking.DTO.SignupRequestDto;
import com.example.ajouparking.Service.SignupService;
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

    @PostMapping("/auth/signup")
    public void signup(SignupRequestDto signupRequestDto){
        signupService.signup(signupRequestDto);
    }
}
