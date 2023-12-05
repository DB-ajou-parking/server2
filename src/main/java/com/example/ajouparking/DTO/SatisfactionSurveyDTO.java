package com.example.ajouparking.DTO;

import lombok.Getter;

@Getter
public class SatisfactionSurveyDTO {

    private int cleanlinessSatisfaction;
    private int facilitySatisfaction;
    private int congestionSatisfaction;
    private int feeSatisfaction;
    private int safetySatisfaction;
    private int signageSatisfaction;
    private int serviceSatisfaction;

    public void setCleanlinessSatisfaction(int cleanlinessSatisfaction) {
        this.cleanlinessSatisfaction = cleanlinessSatisfaction;
    }

    public void setFacilitySatisfaction(int facilitySatisfaction) {
        this.facilitySatisfaction = facilitySatisfaction;
    }

    public void setCongestionSatisfaction(int congestionSatisfaction) {
        this.congestionSatisfaction = congestionSatisfaction;
    }

    public void setFeeSatisfaction(int feeSatisfaction) {
        this.feeSatisfaction = feeSatisfaction;
    }

    public void setSafetySatisfaction(int safetySatisfaction) {
        this.safetySatisfaction = safetySatisfaction;
    }

    public void setSignageSatisfaction(int signageSatisfaction) {
        this.signageSatisfaction = signageSatisfaction;
    }

    public void setServiceSatisfaction(int serviceSatisfaction) {
        this.serviceSatisfaction = serviceSatisfaction;
    }
}