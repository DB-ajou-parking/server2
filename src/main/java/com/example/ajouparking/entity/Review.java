package com.example.ajouparking.entity;

import com.example.ajouparking.dto.ReviewDto;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<User> user;

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
