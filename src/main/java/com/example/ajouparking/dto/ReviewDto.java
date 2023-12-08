package com.example.ajouparking.dto;

import com.example.ajouparking.entity.Review;
import com.example.ajouparking.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewDto {
    private Long id;
    private String reviewText;
    private int likesCount;
    private User user;

    public Review toEntity(){
        return Review.builder()
                .id(id)
                .reviewText(reviewText)
                .likesCount(likesCount)
                .build();
    }
}