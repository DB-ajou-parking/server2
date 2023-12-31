package com.example.ajouparking.controller;

import com.example.ajouparking.dto.CustomUserDetails;
import com.example.ajouparking.dto.ReviewDto;
import com.example.ajouparking.dto.ReviewRequestDto;
import com.example.ajouparking.entity.Review;
import com.example.ajouparking.entity.User;
import com.example.ajouparking.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/parkinglot")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<ReviewDto>> getReviewsForParkingLot(@PathVariable long id, Model model) {
        List<ReviewDto> reviews = reviewService.getReviewsByParkingLotId(id);
        model.addAttribute("review", reviews);
        return ResponseEntity.ok(reviews);
    }


    @PostMapping("/{id}/reviews")
    public void addReviewForParkingLot(@PathVariable Long id, @RequestBody ReviewRequestDto reviewRequestDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = customUserDetails.getUser();
        reviewService.saveReview(user.getId(),id,reviewRequestDto);
    }


}
