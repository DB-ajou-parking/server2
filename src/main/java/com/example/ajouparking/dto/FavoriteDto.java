package com.example.ajouparking.dto;

import com.example.ajouparking.entity.Parkinglot;
import com.example.ajouparking.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteDto {
    private long id;
    private Parkinglot parkinglot;
    private User user;
    //private User userId;

    public void setId(long id) {
        this.id = id;
    }
    public void setParkinglot(Parkinglot parkinglot) {
        this.parkinglot = parkinglot;
    }
    public void setUser(User user) {
        this.user = user;
    }


}