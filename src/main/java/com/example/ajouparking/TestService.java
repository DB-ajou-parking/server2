package com.example.ajouparking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TestService {

    private final TestJpaRepository testJpaRepository ;

    public TestService(TestJpaRepository testJpaRepository) {
        //this.productRepository = productRepository;
        this.testJpaRepository = testJpaRepository;
    }

    public List<TestTable> getParkinglots() {
        List<TestTable> parkingLots = testJpaRepository.findAll();
        log.info("Fetched parking lots: {}", parkingLots);
        return parkingLots;
    }


    public Optional<TestTable> getParkinglotById(long id) {

        Optional<TestTable> parkinglot = testJpaRepository.findById(id);

        if (parkinglot.isPresent()) {
            log.info("---------- Log : getparkinglot : api/parkinglot/{" + id + "} ----------");
            return parkinglot;
        } else {
            log.info("---------- Log : getparkinglot : api/parkinglot/{" + id + "}  ----------");
            return Optional.empty();
        }
    }



    public List<TestTable> getRecordsByLocation(String location) {
        return testJpaRepository.findByLocationRoadNameAddressContainingOrLocationLandParcelAddressContaining(location, location);
    }






}
