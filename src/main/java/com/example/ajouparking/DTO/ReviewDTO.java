package com.example.ajouparking.DTO;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewDTO {
    private String author;
    private String reviewText;
    private LocalDateTime timestamp;

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // constructors, getters, and setters
}