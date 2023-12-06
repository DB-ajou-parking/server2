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

    @Column
    private int cleanlinessSatisfaction;

    @Column
    private int facilitySatisfaction;

    @Column
    private int congestionSatisfaction;

    @Column
    private int feeSatisfaction;

    @Column
    private int safetySatisfaction;
    
    @Column
    private int signageSatisfaction;
    
    @Column
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