package com.example.ajouparking.Controller;

import com.example.ajouparking.DTO.ReviewDTO;
import com.example.ajouparking.Entity.ParkinglotEntity;
import com.example.ajouparking.Entity.ReviewEntity;
import com.example.ajouparking.Entity.SatisfactionSurvey;
import com.example.ajouparking.Repository.SatisfactionSurveyRepository;
import com.example.ajouparking.Service.ParkinglotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class ParkinglotController {


    private final ParkinglotService parkinglotService;

    public ParkinglotController(ParkinglotService parkinglotService, SatisfactionSurveyRepository satisfactionSurveyRepository) {
        this.parkinglotService = parkinglotService;
    }



    @GetMapping("api/test")
    public String test() {
        return "test";
    }

    @GetMapping("api/parkinglots")
    public List<ParkinglotEntity> getParkinglots() {
        return parkinglotService.getParkinglots();
    }

    @GetMapping("api/parkinglot/{id}")
    public ResponseEntity<ParkinglotEntity> getParkinglotById(@PathVariable long id) {
        try {
            Optional<ParkinglotEntity> parkingLot = parkinglotService.getParkinglotById(id);
            return parkingLot.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            // Log the exception or handle it accordingly
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @GetMapping("api/parkinglot/search") //검색란에 입력할시 검색어가 location으로
    public List<ParkinglotEntity> getRecordsByLocation(@RequestParam String location) {
        return parkinglotService.getRecordsByLocation(location);
    }

    @GetMapping("api/parkinglot/select/{location}") //각 지역버튼을 누르면 해당 지역이름이 location으로
    public List<ParkinglotEntity> getParkingLotsByLocation(@PathVariable String location) {
        return parkinglotService.getRecordsByLocation(location);
    }




    @GetMapping("api/parkinglot/{id}/reviews")
    public ResponseEntity<List<ReviewDTO>> getReviewsForParkingLot(@PathVariable long id) {
        List<ReviewDTO> reviews = parkinglotService.getReviewsByParkingLotId(id);

        return ResponseEntity.ok(reviews);
    }

    @PostMapping("api/parkinglot/{id}/reviews")
    public ResponseEntity<String> addReviewForParkingLot(@PathVariable long id, @RequestBody ReviewEntity review) {
        ParkinglotEntity parkinglot = parkinglotService.getParkinglotById(id).orElse(null);

        try {
            if (parkinglot == null) {
                return ResponseEntity.notFound().build();
            }

            review.setParkinglot(parkinglot);
            review.setAuthor(review.getAuthor());
            review.setReviewText(review.getReviewText());
            review.setTimestamp(LocalDateTime.now());
            parkinglotService.saveReview(review);

            return ResponseEntity.ok("Review added successfully");
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }

    }





    @PostMapping("/api/parkinglot/{id}/satisfaction")
    public ResponseEntity<String> saveSatisfactionSurvey(
            @PathVariable Long id,
            @RequestBody SatisfactionSurvey satisfactionSurvey) {

        ParkinglotEntity parkinglot = parkinglotService.getParkinglotById(id).orElse(null);

        try {
            if (parkinglot == null) {
                return ResponseEntity.notFound().build();
            }


            satisfactionSurvey.setParkinglot(parkinglot);
            satisfactionSurvey.setCleanlinessSatisfaction(satisfactionSurvey.getCleanlinessSatisfaction());
            satisfactionSurvey.setFacilitySatisfaction(satisfactionSurvey.getFacilitySatisfaction());
            satisfactionSurvey.setFeeSatisfaction(satisfactionSurvey.getFeeSatisfaction());
            satisfactionSurvey.setSafetySatisfaction(satisfactionSurvey.getSafetySatisfaction());
            satisfactionSurvey.setCongestionSatisfaction(satisfactionSurvey.getCongestionSatisfaction());
            satisfactionSurvey.setSignageSatisfaction(satisfactionSurvey.getSignageSatisfaction());
            satisfactionSurvey.setServiceSatisfaction(satisfactionSurvey.getServiceSatisfaction());
            parkinglotService.saveSatisfactionSurvey(satisfactionSurvey);

            return ResponseEntity.ok("SatisfactionSurvey added successfully");
        }
        catch (Exception e) {
            e.printStackTrace(); // Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }

    }




}



