package com.example.ajouparking.Repository;

import com.example.ajouparking.Entity.ParkinglotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkinglotJpaRepository extends JpaRepository<ParkinglotEntity, Long> {

    List<ParkinglotEntity> findByLocationRoadNameAddressContainingOrLocationLandParcelAddressContaining(String locationRoadNameAddress, String locationLandParcelAddress);


}
