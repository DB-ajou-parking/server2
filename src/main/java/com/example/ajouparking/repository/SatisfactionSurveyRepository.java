package com.example.ajouparking.repository;

import com.example.ajouparking.entity.SatisfactionSurvey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SatisfactionSurveyRepository extends JpaRepository<SatisfactionSurvey, Long> {
    List<SatisfactionSurvey> findByParkinglotId(long parkingLotId);
}
