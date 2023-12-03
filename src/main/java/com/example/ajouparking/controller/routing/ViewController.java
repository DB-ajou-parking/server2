package com.example.ajouparking.controller.routing;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/auth/signin")
    public String signin(){
        return "signin";
    }
    @GetMapping("/favorite")
    public String favorite(){
        return "favorite";
    }
    @GetMapping("/review")
    public String review(){
        return "review";
    }
    @GetMapping("/parkinglotInfo")
    public String parkinglotInfo(){
        return "parkinglotInfo";
    }


}
