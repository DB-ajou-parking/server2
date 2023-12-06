package com.example.ajouparking.entity;

import jakarta.persistence.*;
import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name="review_like_key",
                        columnNames = {"from_user_id","to_review_id"}
                )
        }
)
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "to_review_id")
    private Review review;
}