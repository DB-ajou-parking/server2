package com.example.ajouparking.controller;

import com.example.ajouparking.dto.CustomUserDetails;
import com.example.ajouparking.dto.ReviewDto;
import com.example.ajouparking.dto.ReviewRequestDto;
import com.example.ajouparking.entity.User;
import com.example.ajouparking.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/parkinglot")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{parkinglotId}/reviews")
    public ResponseEntity<List<ReviewDto>> getReviewsForParkingLot(@PathVariable long id) {
        List<ReviewDto> reviews = reviewService.getReviewsByParkingLotId(id);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/{parkinglotId}/reviews")
    public void addReviewForParkingLot(@PathVariable Long parkinglotId, @RequestBody ReviewRequestDto reviewRequestDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = customUserDetails.getUser();
        reviewService.saveReview(user.getId(),parkinglotId,reviewRequestDto);
    }

}
