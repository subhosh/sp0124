package com.sp.trms.domain;

import lombok.*;

import java.time.LocalDate;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class RentalAgreement {
    private String id;
    private String customerId;
    private String storeId;
    private String toolCode;
    private Integer rentalDays;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private Float dailyRentalCharge;
    private Integer chargeDays;
    private Float preDiscountCharge;
    private Integer discountPercent;
    private Float discountAmount;
    private Float finalCharge;
}
