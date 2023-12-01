package com.example.ajouparking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewDto {
    private String author;
    private String reviewText;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
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