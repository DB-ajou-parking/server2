package com.example.ajouparking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ParkinglotService {

    private final ParkinglotJpaRepository parkinglotJpaRepository ;

    public ParkinglotService(ParkinglotJpaRepository testJpaRepository) {
        this.parkinglotJpaRepository = testJpaRepository;
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






}
