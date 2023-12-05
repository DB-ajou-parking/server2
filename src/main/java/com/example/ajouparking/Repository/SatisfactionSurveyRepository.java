package com.example.ajouparking.Repository;

import com.example.ajouparking.Entity.SatisfactionSurvey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SatisfactionSurveyRepository extends JpaRepository<SatisfactionSurvey, Long> {
    List<SatisfactionSurvey> findByParkinglotId(long parkingLotId);
}
