package com.example.ajouparking.repository;

import com.example.ajouparking.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByParkinglotId(long parkingLotId);
}
