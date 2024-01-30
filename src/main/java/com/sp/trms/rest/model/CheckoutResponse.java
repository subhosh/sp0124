package com.sp.trms.rest.model;

import com.sp.trms.domain.ToolBrand;
import com.sp.trms.domain.ToolType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class CheckoutResponse {
    private String toolCode;
    private ToolType toolType;
    private ToolBrand toolBrand;
    private Integer rentalDays;
    private String checkoutDate;
    private String dueDate;
    private String dailyRentalCharge;
    private Integer chargeDays;
    private String preDiscountCharge;
    private String discountPercent;
    private String discountAmount;
    private String finalCharge;

    @Override
    public String toString() {
        return "Rental Agreement Info" + '\n' +
                "----------------------" + '\n' +
                "Tool code: " + toolCode + '\n' +
                "Tool type: " + toolType + '\n' +
                "Tool brand: " + toolBrand + '\n' +
                "Rental days: " + rentalDays + '\n' +
                "Checkout date: " + checkoutDate + '\n' +
                "Due date: " + dueDate + '\n' +
                "Daily rental charge: " + dailyRentalCharge + '\n' +
                "Charge days: " + chargeDays + '\n' +
                "Pre-discount charge: " + preDiscountCharge + '\n' +
                "Discount percent: " + discountPercent + '\n' +
                "Discount amount: " + discountAmount + '\n' +
                "Final charge: " + finalCharge;
    }
}
