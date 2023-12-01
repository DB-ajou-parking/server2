package com.example.ajouparking.repository;

import com.example.ajouparking.entity.Parkinglot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkinglotJpaRepository extends JpaRepository<Parkinglot, Long> {

    List<Parkinglot> findByLocationRoadNameAddressContainingOrLocationLandParcelAddressContaining(String locationRoadNameAddress, String locationLandParcelAddress);


}
