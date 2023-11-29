package com.example.ajouparking;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class TestController {


    private final TestService testService;
    public TestController(TestService testService) {
        this.testService = testService;
    }



    @GetMapping("api/test")
    public String test() {
        return "test";
    }

    @GetMapping("api/parkinglots")
    public List<TestTable> getParkinglots() {
        return testService.getParkinglots();
    }

    @GetMapping("api/parkinglot/{id}")
    public ResponseEntity<TestTable> getParkinglotById(@PathVariable long id) {
        try {
            Optional<TestTable> parkingLot = testService.getParkinglotById(id);
            return parkingLot.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            // Log the exception or handle it accordingly
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @GetMapping("api/parkinglot/search") //검색란에 입력할시 검색어가 location으로
    public List<TestTable> getRecordsByLocation(@RequestParam String location) {
        return testService.getRecordsByLocation(location);
    }

    @GetMapping("api/parkinglot/select/{location}") //각 지역버튼을 누르면 해당 지역이름이 location으로
    public List<TestTable> getParkingLotsByLocation(@PathVariable String location) {
        return testService.getRecordsByLocation(location);
    }


}