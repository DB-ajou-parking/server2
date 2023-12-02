package com.example.ajouparking.entity;

import com.example.ajouparking.dto.ReviewDto;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Parkinglot parkinglot;

    @Column
    private String author;

    @Column
    private String reviewText;


    @Column
    private LocalDateTime timestamp;


    public ReviewDto toDTO() {
        ReviewDto dto = new ReviewDto();
        dto.setAuthor(this.author);
        dto.setReviewText(this.reviewText);
        dto.setTimestamp(this.timestamp);
        return dto;
    }

    // Getters and setters
}
