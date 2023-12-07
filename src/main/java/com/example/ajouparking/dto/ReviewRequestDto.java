package com.example.ajouparking.dto;


import com.example.ajouparking.entity.Parkinglot;
import com.example.ajouparking.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewRequestDto {

    private Parkinglot parkinglot;
    private String reviewText;
    private User user;

}
