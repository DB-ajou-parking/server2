package com.example.ajouparking.controller;

import com.example.ajouparking.dto.ReviewDto;
import com.example.ajouparking.entity.Parkinglot;
import com.example.ajouparking.entity.Review;
import com.example.ajouparking.service.ParkinglotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class ParkinglotController {


    private final ParkinglotService parkinglotService;
    public ParkinglotController(ParkinglotService parkinglotService) {
        this.parkinglotService = parkinglotService;
    }



    @GetMapping("api/test")
    public String test() {
        return "test";
    }

    @GetMapping("api/parkinglots")
    public List<Parkinglot> getParkinglots() {
        return parkinglotService.getParkinglots();
    }

    @GetMapping("api/parkinglot/{id}")
    public ResponseEntity<Parkinglot> getParkinglotById(@PathVariable long id) {
        try {
            Optional<Parkinglot> parkingLot = parkinglotService.getParkinglotById(id);
            return parkingLot.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            // Log the exception or handle it accordingly
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @GetMapping("api/parkinglot/search") //검색란에 입력할시 검색어가 location으로
    public List<Parkinglot> getRecordsByLocation(@RequestParam String location) {
        return parkinglotService.getRecordsByLocation(location);
    }

    @GetMapping("api/parkinglot/select/{location}") //각 지역버튼을 누르면 해당 지역이름이 location으로
    public List<Parkinglot> getParkingLotsByLocation(@PathVariable String location) {
        return parkinglotService.getRecordsByLocation(location);
    }




    @GetMapping("api/parkinglot/{id}/reviews")
    public ResponseEntity<List<ReviewDto>> getReviewsForParkingLot(@PathVariable long id) {
        List<ReviewDto> reviews = parkinglotService.getReviewsByParkingLotId(id);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("api/parkinglot/{id}/reviews")
    public ResponseEntity<String> addReviewForParkingLot(@PathVariable long id, @RequestBody Review review) {
        Parkinglot parkinglot = parkinglotService.getParkinglotById(id).orElse(null);

        if (parkinglot == null) {
            return ResponseEntity.notFound().build();
        }

        review.setParkinglot(parkinglot);
        review.setAuthor(review.getAuthor());
        review.setReviewText(review.getReviewText());
        review.setTimestamp(LocalDateTime.now());
        parkinglotService.saveReview(review);

        return ResponseEntity.ok("Review added successfully");
    }

}