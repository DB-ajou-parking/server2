package com.example.ajouparking.service;

import com.example.ajouparking.exceptionHandler.exceptions.CustomApiException;
import com.example.ajouparking.repository.FavoriteRepository;
import com.example.ajouparking.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    @Transactional
    public void addFavorite(int userId, Long parkinglotId){
        try{
            favoriteRepository.addFavorite(userId,parkinglotId);
        }catch(Exception e){
            throw new CustomApiException("이미 즐겨찾기 추가를 했음");
        }
    }

    @Transactional
    public void deleteFavorite(int userId, Long parkinglotId){
        favoriteRepository.deleteFavorite(userId,parkinglotId);
    }
}
