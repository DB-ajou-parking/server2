package com.example.ajouparking.entity;

import com.example.ajouparking.dto.FavoriteDto;
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
                        name="favorite_key",
                        columnNames = {"parkinglot_id","user_id"}
                )
        }
)
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parkinglot_id")
    private Parkinglot parkinglot;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public FavoriteDto toDTO() {
        FavoriteDto dto = new FavoriteDto();
        dto.setId(this.id);
        dto.setParkinglot(this.parkinglot);
        dto.setUser(this.user);
        return dto;
    }
}