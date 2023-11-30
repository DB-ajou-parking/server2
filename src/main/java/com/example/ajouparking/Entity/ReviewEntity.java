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

    @Getter
    @Column
    private String author;

    @Getter
    @Column
    private String reviewText;

    @Getter
    @Column
    private LocalDateTime timestamp;

    public void setParkinglot(ParkinglotEntity parkinglot) {
        this.parkinglot = parkinglot;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }


}