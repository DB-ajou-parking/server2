package com.example.ajouparking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ParkinglotDto {

    private long id;
    private String ParkingFacilityName;

    public ParkinglotDto(Long id) {
    }

    public void setId(long id) {
        this.id = id;
    }
    public void setParkingFacilityName(String ParkingFacilityName) {
        this.ParkingFacilityName = ParkingFacilityName;
    }

}
