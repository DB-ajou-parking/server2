package com.example.ajouparking.entity;


import com.example.ajouparking.dto.SatisfactionSurveyDTO;
import jakarta.persistence.*;

@Entity
public class SatisfactionSurvey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cleanlinessSatisfaction;
    private int facilitySatisfaction;
    private int congestionSatisfaction;
    private int feeSatisfaction;
    private int safetySatisfaction;
    private int signageSatisfaction;
    private int serviceSatisfaction;

    @ManyToOne
    @JoinColumn(name = "parkinglot_id")
    private Parkinglot parkinglot;

    public void setParkinglot(Parkinglot parkinglot) {
        this.parkinglot = parkinglot;
    }

    public void setCleanlinessSatisfaction(String cleanlinessSatisfaction) {
        this.cleanlinessSatisfaction = Integer.parseInt(cleanlinessSatisfaction);
    }

    public void setFacilitySatisfaction(String facilitySatisfaction) {
        this.facilitySatisfaction = Integer.parseInt(facilitySatisfaction);
    }
    public void setCongestionSatisfaction(String congestionSatisfaction) {
        this.congestionSatisfaction = Integer.parseInt(congestionSatisfaction);
    }
    public void setFeeSatisfaction(String feeSatisfaction) {
        this.feeSatisfaction = Integer.parseInt(feeSatisfaction);
    }
    public void setSafetySatisfaction(String safetySatisfaction) {
        this.safetySatisfaction = Integer.parseInt(safetySatisfaction);
    }
    public void setSignageSatisfaction(String signageSatisfaction) {
        this.signageSatisfaction = Integer.parseInt(signageSatisfaction);
    }
    public void setServiceSatisfaction(String serviceSatisfaction) {
        this.serviceSatisfaction = Integer.parseInt(serviceSatisfaction);
    }









    public Parkinglot getParkinglot() {
        return parkinglot ;
    }

    public String getCleanlinessSatisfaction() {
        return String.valueOf(cleanlinessSatisfaction);
    }
    public String getFacilitySatisfaction() {
        return String.valueOf(facilitySatisfaction);
    }
    public String getCongestionSatisfaction() {
        return String.valueOf(congestionSatisfaction);
    }
    public String getFeeSatisfaction() {
        return String.valueOf(feeSatisfaction);
    }
    public String getSafetySatisfaction() {
        return String.valueOf(safetySatisfaction);
    }
    public String getSignageSatisfaction() {
        return String.valueOf(signageSatisfaction);
    }
    public String getServiceSatisfaction() {
        return String.valueOf(serviceSatisfaction);
    }


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