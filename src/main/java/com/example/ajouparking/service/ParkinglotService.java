package com.example.ajouparking.service;

import com.example.ajouparking.dto.ReviewDto;
import com.example.ajouparking.entity.Parkinglot;
import com.example.ajouparking.entity.Review;
import com.example.ajouparking.repository.ParkinglotJpaRepository;
import com.example.ajouparking.repository.ReviewRepository;
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


    public ParkinglotService(ParkinglotJpaRepository testJpaRepository, ReviewRepository reviewRepository) {
        this.parkinglotJpaRepository = testJpaRepository;
        this.reviewRepository = reviewRepository;
    }
    
    

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



    public List<Parkinglot> getRecordsByLocation(String location) {
        return parkinglotJpaRepository.findByLocationRoadNameAddressContainingOrLocationLandParcelAddressContaining(location, location);
    }



}
