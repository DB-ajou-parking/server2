package com.example.ajouparking.controller;

import com.example.ajouparking.dto.CommonResponseDto;
import com.example.ajouparking.dto.CustomUserDetails;
import com.example.ajouparking.dto.ReviewDto;
import com.example.ajouparking.dto.ReviewRequestDto;
import com.example.ajouparking.entity.Review;
import com.example.ajouparking.entity.User;
import com.example.ajouparking.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/{parkinglotId}/reviews")
    public ResponseEntity<?> getReviewsForParkingLot(@PathVariable long parkinglotId) {
        List<ReviewDto> reviews = reviewService.getReviewsByParkingLotId(parkinglotId);
        return new ResponseEntity<>(new CommonResponseDto<>("리뷰 조회 성공",reviews), HttpStatus.OK);
    }


    @PostMapping("/{parkinglotId}/reviews")
    public ResponseEntity<?> addReviewForParkingLot(@PathVariable Long parkinglotId, @RequestBody ReviewRequestDto reviewRequestDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        User user = customUserDetails.getUser();
        Review review = reviewService.saveReview(user.getId(),parkinglotId,reviewRequestDto);
        return new ResponseEntity<>(new CommonResponseDto<>("리뷰 작성 성공",review), HttpStatus.CREATED);
    }

}
