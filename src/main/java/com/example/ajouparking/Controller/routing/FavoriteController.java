package com.example.ajouparking.Controller.routing;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FavoriteController {

    @GetMapping("/favorite")
    public String favorite(){
        return "favorite";
    }
}
