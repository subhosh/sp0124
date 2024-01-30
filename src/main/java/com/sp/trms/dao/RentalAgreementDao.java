package com.sp.trms.dao;

import com.sp.trms.domain.RentalAgreement;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class RentalAgreementDao {
    private final Map<String, RentalAgreement> agreements = new HashMap<>();

    public void createAgreement(RentalAgreement rentalAgreement) {
        String id = UUID.randomUUID().toString();
        rentalAgreement.setId(id);
        agreements.put(id, rentalAgreement);
    }

    public RentalAgreement getAgreement(String id) {
        return agreements.get(id);
    }
}
