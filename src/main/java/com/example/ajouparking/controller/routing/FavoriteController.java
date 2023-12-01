package com.example.ajouparking.controller.routing;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FavoriteController {

    @GetMapping("/favorite")
    public String favorite(){
        return "favorite";
    }
}
