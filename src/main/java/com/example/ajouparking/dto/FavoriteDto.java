package com.example.ajouparking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteDto {
    private long id;
    private long parkinglotId;
    //private User userId;

    public void setId(long id) {
        this.id = id;
    }
}