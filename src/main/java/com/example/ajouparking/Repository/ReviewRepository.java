package com.example.ajouparking.Repository;

import com.example.ajouparking.Entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    List<ReviewEntity> findByParkinglotId(long parkingLotId);
}
