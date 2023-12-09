package com.example.ajouparking.service;

import com.example.ajouparking.entity.Favorite;
import com.example.ajouparking.exceptionHandler.exceptions.CustomApiException;
import com.example.ajouparking.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    @Autowired
    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }



    public List<Favorite> getFavoritesByUserId(long userId) {
        return favoriteRepository.findByUserId(userId);
    }




    @Transactional
    public void addFavorite(long userId, long parkinglotId){
        try{
            favoriteRepository.addFavorite(userId,parkinglotId);
        }catch(Exception e){
            throw new CustomApiException("이미 즐겨찾기 추가를 했음");
        }
    }

    @Transactional
    public void deleteFavorite(long userId, long parkinglotId){
        favoriteRepository.deleteFavorite(userId,parkinglotId);
    }
}
