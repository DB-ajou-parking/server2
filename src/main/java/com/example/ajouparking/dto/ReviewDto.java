package com.example.ajouparking.dto;

import com.example.ajouparking.entity.Parkinglot;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ReviewDto {
    private Parkinglot parkinglot;
    private String author;
    private String reviewText;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime timestamp;
}