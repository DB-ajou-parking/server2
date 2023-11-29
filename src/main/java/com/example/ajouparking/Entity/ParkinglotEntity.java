package com.example.ajouparking.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class ParkinglotEntity {

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


    @OneToMany(mappedBy = "parkinglot", cascade = CascadeType.ALL)
    private List<ReviewEntity> reviews = new ArrayList<>();






    public Long getId() {
        return id;
    }

    public String getParkingFacilityName() {
        return ParkingFacilityName;
    }

    public String getParkingFacilityClassification() {
        return ParkingFacilityClassification;

    }

    public String getParkingFacilityType() {
        return ParkingFacilityType;
    }

    public String getlocationRoadNameAddress() {
        return locationRoadNameAddress;
    }

    public String getlocationLandParcelAddress() {
        return locationLandParcelAddress;
    }

    public String getNumberofParkingSpaces() {
        return NumberofParkingSpaces;
    }

    public String getAccessClassification() {
        return AccessClassification;
    }

    public String getSubtitleImplementationClassification() {
        return SubtitleImplementationClassification;
    }

    public String getOperatingDays() {
        return OperatingDays;
    }

    public String getWeekdayOperatingStartTime() {
        return WeekdayOperatingStartTime;
    }

    public String getWeekdayOperatingEndTime() {
        return WeekdayOperatingEndTime;
    }

    public String getSaturdayOperatingStartTime() {
        return SaturdayOperatingStartTime;
    }

    public String getSaturdayOperatingEndTime() {
        return SaturdayOperatingEndTime;
    }

    public String getHolidayOperatingStartTime() {
        return HolidayOperatingStartTime;
    }

    public String getHolidayOperatingEndTime() {
        return HolidayOperatingEndTime;
    }

    public String getFeeInformation() {
        return FeeInformation;
    }

    public String getBasicParkingTime() {
        return BasicParkingTime;
    }

    public String getBasicParkingFee() {
        return BasicParkingFee;
    }

    public String getAdditionalUnitTime() {
        return AdditionalUnitTime;
    }

    public String getAdditionalUnitFee() {
        return AdditionalUnitFee;
    }

    public String getDailyParkingTicketApplicationTime() {
        return DailyParkingTicketApplicationTime;
    }

    public String getDailyParkingTicketFee() {
        return DailyParkingTicketFee;
    }

    public String getMonthlySubscriptionFee() {
        return MonthlySubscriptionFee;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public String getSpecialNotes() {
        return SpecialNotes;
    }

    public String getManagementOrganizationName() {
        return ManagementOrganizationName;
    }

    public String getTelephoneNumber() {
        return TelephoneNumber;
    }

    public String getLatitude() {
        return Latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public String getPresenceofDisabledParkingSpaces() {
        return PresenceofDisabledParkingSpaces;
    }

    public String getDataReferenceDate() {
        return DataReferenceDate;
    }

    public String getProvidingOrganizationCode() {
        return ProvidingOrganizationCode;
    }

    public String getprovidingOrganizationName() {
        return providingOrganizationName;
    }



}