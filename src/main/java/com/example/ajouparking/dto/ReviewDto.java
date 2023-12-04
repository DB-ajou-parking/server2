package com.example.ajouparking.dto;

import com.example.ajouparking.entity.Review;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ReviewDto {
    private String author;
    private String reviewText;

    public Review toEntity(){
        return Review.builder()
                .author(author)
                .reviewText(reviewText)
                .build();
    }
}