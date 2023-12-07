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
    private int id;

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
        if(this.tierExp - exp < 0){
            this.tierExp = 0;
        }
        this.tierExp = tierExp - exp;
    }

    public void changeTier(int exp){
        if(this.tierExp + exp == 1){
            this.tier = "Bronze";
        }else if(this.tierExp + exp == 2){
            this.tier = "Silver";
        }else{
            this.tier = "Gold";
        }
    }
}
