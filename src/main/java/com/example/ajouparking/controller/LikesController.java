package com.example.ajouparking.controller;

import com.example.ajouparking.dto.CommonResponseDto;
import com.example.ajouparking.dto.CustomUserDetails;
import com.example.ajouparking.entity.User;
import com.example.ajouparking.repository.ReviewRepository;
import com.example.ajouparking.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikesController {

    private final LikesService likesService;
    private final ReviewRepository reviewRepository;
    @PostMapping("/{toReviewId}")
    public ResponseEntity<?> like(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long toReviewId){
        User user = customUserDetails.getUser();
        likesService.putLike(user.getId(),toReviewId);

        URI selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest() .toUriString());
        return ResponseEntity.created(selfLink).build();
    }

    @DeleteMapping("/{toReviewId}")
    public ResponseEntity<?> unlike(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable Long toReviewId){
        User user = customUserDetails.getUser();
        likesService.deleteLike(user.getId(), toReviewId);
        return new ResponseEntity<>(new CommonResponseDto<>("삭제성공",null),HttpStatus.NO_CONTENT);
    }

}
