package com.example.ajouparking.controller;

import com.example.ajouparking.dto.ReviewDto;
import com.example.ajouparking.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/parkinglot/{id}/reviews")
    public ResponseEntity<List<ReviewDto>> getReviewsForParkingLot(@PathVariable long id) {
        List<ReviewDto> reviews = reviewService.getReviewsByParkingLotId(id);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/parkinglot/{id}/reviews")
    public void addReviewForParkingLot(@PathVariable long id, @RequestBody ReviewDto reviewDto) {
        reviewService.saveReview(id,reviewDto);
    }

}
