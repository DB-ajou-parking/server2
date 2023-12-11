package com.example.ajouparking.service;

import com.example.ajouparking.dto.FavoriteDto;
import com.example.ajouparking.entity.Favorite;
import com.example.ajouparking.exceptionHandler.exceptions.CustomApiException;
import com.example.ajouparking.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    @Autowired
    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }


    @Transactional(readOnly = true)
    public List<FavoriteDto> getUserFavorites(long userId) {
        List<Favorite> favorites = favoriteRepository.findByUserId(userId);

        // Filter favorites with both parkinglot and user as null
        List<FavoriteDto> filteredFavorites = favorites.stream()
                .filter(favorite -> favorite.getParkinglot() != null || favorite.getUser() != null)
                .map(favorite -> new FavoriteDto(
                        favorite.getId(),
                        favorite.getParkinglot() != null ? favorite.getParkinglot().getId() : null
                ))
                .collect(Collectors.toList());

        return filteredFavorites;
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
