package com.example.ajouparking.Service;

import com.example.ajouparking.DTO.ReviewDTO;
import com.example.ajouparking.Entity.ParkinglotEntity;
import com.example.ajouparking.Entity.ReviewEntity;
import com.example.ajouparking.Repository.ParkinglotJpaRepository;
import com.example.ajouparking.Repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ParkinglotService {

    private final ParkinglotJpaRepository parkinglotJpaRepository ;
    private final ReviewRepository reviewRepository;


    public ParkinglotService(ParkinglotJpaRepository ParkinglotJpaRepository,ReviewRepository ReviewRepository) {
        this.parkinglotJpaRepository = ParkinglotJpaRepository;
        this.reviewRepository = ReviewRepository;
    }


    public List<ParkinglotEntity> getParkinglots() {
        List<ParkinglotEntity> parkingLots = parkinglotJpaRepository.findAll();
        log.info("Fetched parking lots: {}", parkingLots);
        return parkingLots;
    }


    public Optional<ParkinglotEntity> getParkinglotById(long id) {

        Optional<ParkinglotEntity> parkinglot = parkinglotJpaRepository.findById(id);

        if (parkinglot.isPresent()) {
            log.info("---------- Log : getparkinglot : api/parkinglot/{" + id + "} ----------");
            return parkinglot;
        } else {
            log.info("---------- Log : getparkinglot : api/parkinglot/{" + id + "}  ----------");
            return Optional.empty();
        }
    }



    public List<ParkinglotEntity> getRecordsByLocation(String location) {
        return parkinglotJpaRepository.findByLocationRoadNameAddressContainingOrLocationLandParcelAddressContaining(location, location);
    }



    public List<ReviewDTO> getReviewsByParkingLotId(long parkingLotId) {
        List<ReviewEntity> reviews = reviewRepository.findByParkinglotId(parkingLotId);
        return reviews.stream().map(ReviewEntity::toDTO).collect(Collectors.toList());
    }

    public void saveReview(ReviewEntity review) {
        reviewRepository.save(review);
    }








}
