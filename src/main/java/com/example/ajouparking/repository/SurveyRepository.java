package com.example.ajouparking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyRepository extends JpaRepository<com.example.ajouparking.entity.Survey, Long> {
    List<com.example.ajouparking.entity.Survey> findByParkinglotId(long parkingLotId);
}
