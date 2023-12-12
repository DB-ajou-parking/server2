package com.example.ajouparking.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "tier", nullable = false)
    private String tier;

    @Column(name = "tier_exp")
    private int tierExp;

    @PrePersist
    public void defaultTier(){
        this.tier = "un_lank";
    }

    public void plusExp(int exp){
        this.tierExp = tierExp + exp;
    }

    public void minusExp(int exp){
        if(this.tierExp < 0){ return; }
        this.tierExp = tierExp - exp;
    }

    public void updateTier() {
        if (tierExp >= 10) {
            tier = "다이아몬드";
        } else if (tierExp >= 5) {
            tier = "플레티넘";
        } else if (tierExp >= 3) {
            tier = "골드";
        } else if (tierExp >= 1) {
            tier = "실버";
        } else {
            tier = "브론즈";
        }
    }


}
