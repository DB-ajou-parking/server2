package com.example.ajouparking.controller;

import com.example.ajouparking.dto.CustomUserDetails;
import com.example.ajouparking.entity.User;
import com.example.ajouparking.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MyInfoController {

    private final UserService userService;

    public MyInfoController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/myinfo")
    public User getMyInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        // Retrieve user information using the userService
        // The UserDetails is injected automatically when a user is logged in
        return userService.findUserById(userDetails.getUserId());
    }
}