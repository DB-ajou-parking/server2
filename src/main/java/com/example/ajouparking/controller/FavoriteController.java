package com.example.ajouparking.controller;

import com.example.ajouparking.dto.CommonResponseDto;
import com.example.ajouparking.dto.CustomUserDetails;
import com.example.ajouparking.entity.Favorite;
import com.example.ajouparking.entity.User;
import com.example.ajouparking.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;


    @GetMapping("/user")
    public ResponseEntity<List<Favorite>> getFavoriteByUser(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        long userId = customUserDetails.getUser().getId();
        List<Favorite> favorites = favoriteService.getFavoritesByUserId(userId);
        return new ResponseEntity<>(favorites, HttpStatus.OK);
    }

    @PostMapping("/{parkinglotId}")
    public ResponseEntity<?> addFavorite(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable long parkinglotId){
        User user = customUserDetails.getUser();
        favoriteService.addFavorite(user.getId(),parkinglotId);

        URI selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest() .toUriString());
        return ResponseEntity.created(selfLink).build();
    }

    @DeleteMapping("/{parkinglotId}")
    public ResponseEntity<?> deleteFavorite(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable long parkinglotId){
        User user = customUserDetails.getUser();
        favoriteService.deleteFavorite(user.getId(), parkinglotId);
        return new ResponseEntity<>(new CommonResponseDto<>("삭제성공",null),HttpStatus.NO_CONTENT);
    }
}
