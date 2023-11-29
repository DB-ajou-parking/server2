package com.example.ajouparking.Entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @ManyToOne
    @JoinColumn
    private ParkinglotEntity parkinglot;

    @Column
    private String author;

    @Column
    private String reviewText;

    @Column
    private LocalDateTime timestamp;

    public void setParkinglot(ParkinglotEntity parkinglot) {
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


    // Getters and setters
}
