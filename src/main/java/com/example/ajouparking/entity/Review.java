package com.example.ajouparking.entity;

import com.example.ajouparking.dto.ReviewDto;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @ManyToOne
    @JoinColumn
    private Parkinglot parkinglot;

    @Column
    private String author;

    @Column
    private String reviewText;


    @Column
    private LocalDateTime timestamp;

    public void setParkinglot(Parkinglot parkinglot) {
        this.parkinglot = parkinglot;
    }

    public void setAuthor(String author) {
        this.author = author;
    }public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getAuthor() {
        return author;
    }

    public String getReviewText() {
        return reviewText;
    }


    public ReviewDto toDTO() {
        ReviewDto dto = new ReviewDto();
        dto.setAuthor(this.author);
        dto.setReviewText(this.reviewText);
        dto.setTimestamp(this.timestamp);
        return dto;
    }

    // Getters and setters
}
