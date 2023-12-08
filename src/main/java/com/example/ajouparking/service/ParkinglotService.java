package com.example.ajouparking.service;

import com.example.ajouparking.dto.ReviewDto;
import com.example.ajouparking.entity.Parkinglot;
import com.example.ajouparking.entity.Review;
import com.example.ajouparking.entity.Survey;
import com.example.ajouparking.repository.ParkinglotJpaRepository;
import com.example.ajouparking.repository.ReviewRepository;

import com.example.ajouparking.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParkinglotService {

    private final ParkinglotJpaRepository parkinglotJpaRepository ;
    private final ReviewRepository reviewRepository;
    private final SurveyRepository surveyRepository;

    
    

    public List<Parkinglot> getParkinglots() {
        List<Parkinglot> parkingLots = parkinglotJpaRepository.findAll();
        log.info("Fetched parking lots: {}", parkingLots);
        return parkingLots;
    }


    public Optional<Parkinglot> getParkinglotById(long id) {

        Optional<Parkinglot> parkinglot = parkinglotJpaRepository.findById(id);

        if (parkinglot.isPresent()) {
            log.info("---------- Log : getparkinglot : api/parkinglot/{" + id + "} ----------");
            return parkinglot;
        } else {
            log.info("---------- Log : getparkinglot : api/parkinglot/{" + id + "}  ----------");
            return Optional.empty();
        }
    }

    public Parkinglot saveParkingLot(Parkinglot parkingLot) {
        return parkinglotJpaRepository.save(parkingLot);
    }


    public List<Parkinglot> getRecordsByLocation(String location) {
        return parkinglotJpaRepository.findByLocationRoadNameAddressContainingOrLocationLandParcelAddressContaining(location, location);
    }


    public List<ReviewDto> getReviewsByParkingLotId(long parkingLotId) {
        List<Review> reviews = reviewRepository.findByParkinglotId(parkingLotId);
        return reviews.stream().map(Review::toDTO).collect(Collectors.toList());
    }

    public void saveReview(Review review) {
        reviewRepository.save(review);
    }




    public List<com.example.ajouparking.dto.SatisfactionSurveyDTO> getSatisfactionSurveyByParkingLotId(long parkingLotId) {
        List<Survey> surveys = surveyRepository.findByParkinglotId(parkingLotId);
        return surveys.stream().map(Survey::toDTO).collect(Collectors.toList());
    }


    public void saveSatisfactionSurvey(Survey survey) {
        this.surveyRepository.save(survey);
    }








}
