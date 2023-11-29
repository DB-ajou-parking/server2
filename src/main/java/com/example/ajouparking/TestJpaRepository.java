package com.example.ajouparking;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestJpaRepository extends JpaRepository<TestTable, Long> {

    List<TestTable> findByLocationRoadNameAddressContainingOrLocationLandParcelAddressContaining(String locationRoadNameAddress, String locationLandParcelAddress);


}
