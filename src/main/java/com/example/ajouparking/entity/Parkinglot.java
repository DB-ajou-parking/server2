package com.example.ajouparking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@Entity
public class Parkinglot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String ParkingFacilityName;

    @Column
    private String ParkingFacilityClassification;

    @Column
    private String ParkingFacilityType;

    @Column
    private String locationRoadNameAddress;

    @Column
    private String locationLandParcelAddress;

    @Column
    private String NumberofParkingSpaces;

    @Column
    private String AccessClassification;

    @Column
    private String SubtitleImplementationClassification;

    @Column
    private String OperatingDays;

    @Column
    private String WeekdayOperatingStartTime;

    @Column
    private String WeekdayOperatingEndTime;

    @Column
    private String SaturdayOperatingStartTime;

    @Column
    private String SaturdayOperatingEndTime;

    @Column
    private String HolidayOperatingStartTime;

    @Column
    private String HolidayOperatingEndTime;

    @Column
    private String FeeInformation;

    @Column
    private String BasicParkingTime;

    @Column
    private String BasicParkingFee;

    @Column
    private String AdditionalUnitTime;

    @Column
    private String AdditionalUnitFee;

    @Column
    private String DailyParkingTicketApplicationTime;

    @Column
    private String DailyParkingTicketFee;

    @Column
    private String MonthlySubscriptionFee;

    @Column
    private String PaymentMethod;

    @Column
    private String SpecialNotes;

    @Column
    private String ManagementOrganizationName;

    @Column
    private String TelephoneNumber;

    @Column
    private String Latitude;

    @Column
    private String Longitude;

    @Column
    private String PresenceofDisabledParkingSpaces;

    @Column
    private String DataReferenceDate;

    @Column
    private String ProvidingOrganizationCode;

    @Column
    private String providingOrganizationName;

    @Column String distance;

    @OneToMany(mappedBy = "parkinglot", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> reviews = new ArrayList<>();


}