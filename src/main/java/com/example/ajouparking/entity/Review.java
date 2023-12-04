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
    @JoinColumn(name = "parkinglot_id")
    private Parkinglot parkinglot;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String reviewText;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @PrePersist
    public void createdAt(){
        this.createdAt = LocalDateTime.now();
    }

    public ReviewDto toDTO() {
        ReviewDto dto = new ReviewDto();
        dto.setAuthor(this.author);
        dto.setReviewText(this.reviewText);
        return dto;
    }

}
