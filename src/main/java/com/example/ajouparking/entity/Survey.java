package com.example.ajouparking.entity;


import com.example.ajouparking.dto.SatisfactionSurveyDTO;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "청결_만족도")
    private int cleanlinessSatisfaction;

    @Column(name = "시설_만족도")
    private int facilitySatisfaction;

    @Column(name = "혼잡_만족도")
    private int congestionSatisfaction;

    @Column(name = "요금_만족도")
    private int feeSatisfaction;

    @Column(name = "안전성_만족도")
    private int safetySatisfaction;
    
    @Column(name = "표지판_만족도")
    private int signageSatisfaction;
    
    @Column(name = "서비스_만족도")
    private int serviceSatisfaction;

    @ManyToOne
    @JoinColumn(name = "parkinglot_id")
    private Parkinglot parkinglot;

    public SatisfactionSurveyDTO toDTO() {
        SatisfactionSurveyDTO dto = new SatisfactionSurveyDTO();
        dto.setCleanlinessSatisfaction(this.cleanlinessSatisfaction);
        dto.setFacilitySatisfaction(this.facilitySatisfaction);
        dto.setCongestionSatisfaction(this.congestionSatisfaction);
        dto.setFeeSatisfaction(this.feeSatisfaction);
        dto.setSafetySatisfaction(this.safetySatisfaction);
        dto.setSignageSatisfaction(this.signageSatisfaction);
        dto.setServiceSatisfaction(this.serviceSatisfaction);
        return dto;
    }
}