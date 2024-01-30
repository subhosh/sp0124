/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.sp.trms.service;

import com.sp.trms.domain.*;
import com.sp.trms.rest.model.CheckoutRequest;
import com.sp.trms.rest.model.CheckoutResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class TrmsApplicationTests {
    @Mock
    UserService userService;
    @Mock
    ToolService toolService;
    @Spy
    RentalDayService rentalDayService;
    @Mock
    PricingService pricingService;
    @Mock
    RentalAgreementService rentalAgreementService;
    @InjectMocks
    TrmsService trmsService;

    @Test
    public void testCheckout_HappyPath_NoHolidays() {
        //Prepare data for the call to checkout
        String toolCode = "LADW";
        ToolType toolType = ToolType.Ladder;
        CheckoutRequest checkoutRequest = getCheckoutRequest(toolCode);
        Price price = getPrice(toolType);

        //Expected values
        float discountAmount = 1.00f;
        float preDiscountAmount = 9.95f;
        float finalCharge = 8.95f;

        //Mock methods for unit testing
        when(toolService.getTool(toolCode)).thenReturn(getTool(toolCode));
        when(pricingService.getToolPrice(toolType)).thenReturn(price);
        when(pricingService.round(price.getDailyCharge() * checkoutRequest.getRentalDayCount()))
                .thenReturn(preDiscountAmount);
        when(pricingService.round(preDiscountAmount * checkoutRequest.getDiscountPercent() / 100))
                .thenReturn(preDiscountAmount - finalCharge);
        when(pricingService.round(preDiscountAmount - discountAmount))
                .thenReturn(finalCharge);
        when(rentalAgreementService.createRentalAgreement(checkoutRequest.getCustomerId(),
                checkoutRequest.getRentalDayCount(), price.getDailyCharge(),
                checkoutRequest.getCheckoutDate().plusDays(checkoutRequest.getRentalDayCount()),
                checkoutRequest.getRentalDayCount(), checkoutRequest.getStoreId(), checkoutRequest.getCheckoutDate(),
                checkoutRequest.getDiscountPercent(), finalCharge, discountAmount, preDiscountAmount, toolCode))
                .thenReturn(new RentalAgreement());

        CheckoutResponse response = trmsService.checkout(checkoutRequest);
        assertNotNull(response, "Response cannot be null. Error in the test.");
        assertEquals("$8.95", response.getFinalCharge());
        assertEquals("$9.95", response.getPreDiscountCharge());
        assertEquals("$1.00", response.getDiscountAmount());
        assertEquals(toolType, response.getToolType());
        //Assert all the other fields here.
    }

    @Test
    public void testCheckout_HappyPath_WithHolidays() {
        //TODO Test checkout function with some holidays returned by rental day service.
    }

    @Test
    public void testCheckout_HappyPath_PriceCases() {
        //TODO Test checkout function with various combinations of prices holiday/weekend/weekday
    }

    @Test
    public void testCheckout_RoundingEdgeCases() {
        //TODO Test checkout function with various combinations of prices.
    }

    @Test
    public void testCheckout_NullExceptionCases() {
        //TODO Test checkout function with nulls for some values.
    }

    @Test
    public void testCheckout_DataNotFoundExceptions() {
        //TODO Test checkout function with tool/price data that is not in the system.
    }

    private static Price getPrice(ToolType toolType) {
        return new Price(null, toolType, 1.99f, Boolean.FALSE, Boolean.TRUE, Boolean.TRUE);
    }

    private static Tool getTool(String toolCode) {
        Tool tool = new Tool();
        tool.setCode(toolCode);
        tool.setType(ToolType.Ladder);
        tool.setBrand(ToolBrand.Stihl);
        return tool;
    }

    private static CheckoutRequest getCheckoutRequest(String toolCode) {
        CheckoutRequest checkoutRequest = new CheckoutRequest();
        checkoutRequest.setCustomerId("1");
        checkoutRequest.setCheckoutDate(LocalDate.now());
        checkoutRequest.setStoreId("1");
        checkoutRequest.setStoreAssociateId("1");
        checkoutRequest.setToolCode(toolCode);
        checkoutRequest.setDiscountPercent(10);
        checkoutRequest.setRentalDayCount(5);
        return checkoutRequest;
    }
}
