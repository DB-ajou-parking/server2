package com.example.ajouparking.dto;

import com.example.ajouparking.entity.Review;
import com.example.ajouparking.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewDto {
    //private String author;
    private String reviewText;
    private int likesCount;
    private User user;

    public Review toEntity(){
        return Review.builder()
                //.author(author)
                .reviewText(reviewText)

                .build();
    }
}