package com.example.ajouparking.service;

import com.example.ajouparking.dto.CommonResponseDto;
import com.example.ajouparking.dto.ReviewDto;
import com.example.ajouparking.dto.ReviewRequestDto;
import com.example.ajouparking.entity.Parkinglot;
import com.example.ajouparking.entity.Review;
import com.example.ajouparking.entity.User;
import com.example.ajouparking.exceptionHandler.exceptions.CustomApiException;
import com.example.ajouparking.exceptionHandler.exceptions.CustomValidationException;
import com.example.ajouparking.repository.ReviewRepository;
import com.example.ajouparking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ParkinglotService parkinglotService;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<ReviewDto> getReviewsByParkingLotId(long parkingLotId) {
        List<Review> reviews = reviewRepository.findByParkinglotId(parkingLotId);
        return reviews.stream().map(Review::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public ResponseEntity<?> saveReview(long userId, Long parkingLotId, ReviewRequestDto reviewRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("없는유저"));
        Parkinglot parkinglot = parkinglotService.getParkinglotById(parkingLotId).orElseThrow(
                () -> new IllegalArgumentException("없는 주차장 입니다."));

        if (parkinglot == null) {
            throw new CustomValidationException("없는 주차장 입니다.");
        }

        try {
            Review reviewEntity = Review.builder()
                    .user(user)
                    .parkinglot(parkinglot)
                    .reviewText(reviewRequestDto.getReviewText())
                    .build();

            reviewRepository.save(reviewEntity);
            return new ResponseEntity<>(new CommonResponseDto<>("리뷰 등록 성공",reviewEntity),HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(new CustomApiException("리뷰 등록 실패"), HttpStatus.BAD_REQUEST);
        }

    }




}
