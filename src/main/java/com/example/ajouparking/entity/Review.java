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

    @Column(name = "review_text", nullable = false)
    private String reviewText;

    @Column(name="likes_count")
    private int likesCount;

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
        dto.setReviewText(this.reviewText);
        dto.setLikesCount(this.likesCount);
        dto.setUser(this.user);
        return dto;
    }

}
