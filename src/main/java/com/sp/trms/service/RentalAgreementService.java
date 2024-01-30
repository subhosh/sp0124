package com.sp.trms.service;

import com.sp.trms.dao.RentalAgreementDao;
import com.sp.trms.domain.RentalAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Service to handle rental agreements including building, creating and updating agreements.
 */
@Service
public class RentalAgreementService {
    private final RentalAgreementDao rentalAgreementDao;

    public RentalAgreementService(@Autowired RentalAgreementDao rentalAgreementDao) {
        this.rentalAgreementDao = rentalAgreementDao;
    }

    /**
     * Create a rental agreement and persists in the in-memory database.
     * @param customerId - Customer Id.
     * @param rentalDays - Rental Days.
     * @param dailyRentalCharge - Daily Rental Charge for the tool.
     * @param dueDate - Due Date.
     * @param chargeDays - Chargable Days.
     * @param storeId - Store id.
     * @param checkoutDate - Checkout Date.
     * @param discountPercent - Discount Percent.
     * @param finalCharge - Final Charge.
     * @param discountAmount - Discount Amount.
     * @param preDiscountCharge - Pre-Discount Charge.
     * @param toolCode - Tool Code.
     * @return - Rental Agreement with id generated.
     */
    public RentalAgreement createRentalAgreement(String customerId, int rentalDays, float dailyRentalCharge, LocalDate dueDate,
                                        int chargeDays, String storeId, LocalDate checkoutDate, int discountPercent,
                                        float finalCharge, float discountAmount, float preDiscountCharge,
                                        String toolCode) {
        RentalAgreement agreement = RentalAgreement.builder()
                                        .customerId(customerId)
                                        .rentalDays(rentalDays)
                                        .dailyRentalCharge(dailyRentalCharge)
                                        .dueDate(dueDate)
                                        .chargeDays(chargeDays)
                                        .storeId(storeId)
                                        .checkoutDate(checkoutDate)
                                        .discountPercent(discountPercent)
                                        .finalCharge(finalCharge)
                                        .discountAmount(discountAmount)
                                        .preDiscountCharge(preDiscountCharge)
                                        .toolCode(toolCode)
                                        .build();
        rentalAgreementDao.createAgreement(agreement);
        return agreement;
    }
}
