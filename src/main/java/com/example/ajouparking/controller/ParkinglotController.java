package com.example.ajouparking.controller;

import com.example.ajouparking.dto.SatisfactionSurveyDTO;
import com.example.ajouparking.entity.Survey;
import com.example.ajouparking.entity.Parkinglot;
import com.example.ajouparking.service.ParkinglotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ParkinglotController {


    private final ParkinglotService parkinglotService;


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


    @GetMapping("api/parkinglot/{id}/satisfaction")
    public ResponseEntity<List<SatisfactionSurveyDTO>> getSatisfactionSurveyForParkingLot(@PathVariable long id) {
        List<SatisfactionSurveyDTO> satisfactionSurvey = parkinglotService.getSatisfactionSurveyByParkingLotId(id);

        return ResponseEntity.ok(satisfactionSurvey);
    }

    @PostMapping("/api/parkinglot/{id}/satisfaction")
    public ResponseEntity<?> saveSatisfactionSurvey(@PathVariable Long id, @RequestBody Survey survey) {

        Parkinglot parkinglot = parkinglotService.getParkinglotById(id).orElse(null);

        try {
            if (parkinglot == null) {
                return ResponseEntity.notFound().build();
            }


            survey.setParkinglot(parkinglot);
            survey.setCleanlinessSatisfaction(survey.getCleanlinessSatisfaction());
            survey.setFacilitySatisfaction(survey.getFacilitySatisfaction());
            survey.setFeeSatisfaction(survey.getFeeSatisfaction());
            survey.setSafetySatisfaction(survey.getSafetySatisfaction());
            survey.setCongestionSatisfaction(survey.getCongestionSatisfaction());
            survey.setSignageSatisfaction(survey.getSignageSatisfaction());
            survey.setServiceSatisfaction(survey.getServiceSatisfaction());
            parkinglotService.saveSatisfactionSurvey(survey);

            return ResponseEntity.ok("SatisfactionSurvey added successfully");
        }
        catch (Exception e) {
            e.printStackTrace(); // Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }

    }




}



