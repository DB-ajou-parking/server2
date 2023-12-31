package com.example.ajouparking.repository;

import com.example.ajouparking.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite,Long> {


    @Modifying
    @Query(value = "insert into favorite(user_id,parkinglot_id) values(:userId,:parkinglotId)",nativeQuery = true)
    void addFavorite(long userId,long parkinglotId);

    @Modifying
    @Query(value = "delete from favorite where user_id = :userId and parkinglot_id = :parkinglotId",nativeQuery=true)
    void deleteFavorite(long userId, long parkinglotId);


    List<Favorite> findByUserId(long userId);
}
