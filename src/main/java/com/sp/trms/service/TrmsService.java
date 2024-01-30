package com.sp.trms.service;

import com.sp.trms.domain.*;
import com.sp.trms.exception.RentalDataNotFoundException;
import com.sp.trms.rest.model.CheckoutRequest;
import com.sp.trms.rest.model.CheckoutResponse;
import com.sp.trms.util.FormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.List;

/**
 * Service Class to perform checkout process and corresponding helper methods.
 */
@Service
public class TrmsService {
    private final UserService userService;
    private final ToolService toolService;
    private final RentalDayService rentalDayService;
    private final PricingService pricingService;
    private final RentalAgreementService rentalAgreementService;

    @Autowired
    public TrmsService(UserService userService,
                       ToolService toolService,
                       RentalDayService rentalDayService,
                       PricingService pricingService,
                       RentalAgreementService rentalAgreementService) {
        this.userService = userService;
        this.toolService = toolService;
        this.rentalDayService = rentalDayService;
        this.pricingService = pricingService;
        this.rentalAgreementService = rentalAgreementService;
    }

    /**
     * Service method to handle checkout process. Essentially generate a rental agreement and return the response.
     * @param checkoutRequest - Request object to get checkout info.
     * @return CheckoutResponse - Response object to have rental agreement info.
     * @throws RentalDataNotFoundException - Checked exception to return any data errors.
     */
    public CheckoutResponse checkout(CheckoutRequest checkoutRequest) throws RentalDataNotFoundException {
        //Populate data from input - Tool info, rental days and price etc.
        Tool tool = toolService.getTool(checkoutRequest.getToolCode());
        LocalDate checkoutDate = checkoutRequest.getCheckoutDate();
        LocalDate dueDate = checkoutDate.plusDays(checkoutRequest.getRentalDayCount());
        List<RentalDay> rentalDays = rentalDayService.getRentalDays(checkoutDate, dueDate);
        Price price = pricingService.getToolPrice(tool.getType());

        //Time to calculate chargable rental days and corresponding charges with decimal rounding.
        int chargeDays = getChargeDays(price, rentalDays);
        float dailyCharge = price.getDailyCharge();
        float grossCharge = pricingService.round(dailyCharge * chargeDays);
        float discountAmount = pricingService.round(checkoutRequest.getDiscountPercent() * grossCharge / 100);
        float finalCharge = pricingService.round(grossCharge - discountAmount);

        //Generate rental agreement with the info and persist in the database (in-memory)
        RentalAgreement rentalAgreement = rentalAgreementService.createRentalAgreement(checkoutRequest.getCustomerId(),
                checkoutRequest.getRentalDayCount(), dailyCharge, dueDate, chargeDays, checkoutRequest.getStoreId(),
                checkoutDate, checkoutRequest.getDiscountPercent(), finalCharge, discountAmount, grossCharge,
                tool.getCode());

        //Assign the generated rental agreement to the customer
        userService.addAgreementToCustomer(checkoutRequest.getCustomerId(), rentalAgreement);

        //Format and generate response data to be returned.
        CheckoutResponse checkoutResponse = buildResponse(checkoutRequest, chargeDays, checkoutDate, finalCharge,
                dailyCharge, discountAmount, dueDate, grossCharge, tool);

        //Printing to console for debugging purposes and needed to change this to logging with debug level.
        System.out.println(checkoutResponse);

        return checkoutResponse;
    }

    /**
     * Method to build response object based on the agreement data / input request.
     * @param checkoutRequest - Checkout Request/
     * @param chargeDays - Days that can be charged.
     * @param checkoutDate - Checkout Date.
     * @param finalCharge - Final Charge for usage.
     * @param dailyCharge - Daily Rental Charge.
     * @param discountAmount - Discount Amount.
     * @param dueDate - Due date to return rental tool.
     * @param grossCharge - Gross Charge before any discounts.
     * @param tool - Tool object
     * @return CheckoutResponse object with formatted response.
     */
    private static CheckoutResponse buildResponse(CheckoutRequest checkoutRequest, int chargeDays,
                                                  LocalDate checkoutDate, float finalCharge,
                                                  float dailyCharge, float discountAmount,
                                                  LocalDate dueDate, float grossCharge, Tool tool) {
        return CheckoutResponse.builder()
                .chargeDays(chargeDays)
                .checkoutDate(FormatUtil.formatDate(checkoutDate))
                .finalCharge(FormatUtil.formatCurrency(finalCharge))
                .dailyRentalCharge(FormatUtil.formatCurrency(dailyCharge))
                .discountAmount(FormatUtil.formatCurrency(discountAmount))
                .discountPercent(FormatUtil.formatPercent(checkoutRequest.getDiscountPercent()))
                .dueDate(FormatUtil.formatDate(dueDate))
                .preDiscountCharge(FormatUtil.formatCurrency(grossCharge))
                .rentalDays(checkoutRequest.getRentalDayCount())
                .toolBrand(tool.getBrand())
                .toolCode(tool.getCode())
                .toolType(tool.getType())
                .build();
    }

    /**
     * Method to calculate chargable days by excluding non chargable days based on holidays/weekends
     * as well as pricing configuration (some tools are free to use on holidays/weekends)
     * @param price - Price object with config.
     * @param rentalDays - Rental days including all calendar days.
     * @return Number of chargable days to the customer
     */
    protected int getChargeDays(Price price, List<RentalDay> rentalDays) {
        int noOfChargeDays = 0;

        //Loop through rental days and see if its a holiday or a weekend and corresponding pricing config
        for (RentalDay rentalDay : rentalDays) {
            if (rentalDay.getDayType() == DayType.HOLIDAY && price.getIsHolidayCharge()) {
                noOfChargeDays++;
            } else if (rentalDay.getDayType() == DayType.WEEKEND && price.getIsWeekEndCharge()) {
                noOfChargeDays++;
            } else if (rentalDay.getDayType() == DayType.WEEKDAY && price.getIsWeekDayCharge()) {
                noOfChargeDays++;
            }
        }

        return  noOfChargeDays;
    }

    /**
     * Method to perform input validations especially user ids and tool code.
     * @param checkoutRequest - Checkout Request
     * @throws RentalDataNotFoundException - Checked exception to throw for data issues.
     */
    public void validateCheckoutRequest(CheckoutRequest checkoutRequest) throws RentalDataNotFoundException {
        //Validate users (TODO: Move to session management)
        validateUsers(checkoutRequest.getCustomerId(), checkoutRequest.getStoreAssociateId());

        //Validate Tool Info
        validateToolCode(checkoutRequest.getToolCode());
    }

    /**
     * Method to validate tool code exists in the system.
     * @param toolCode - Tool Code
     * @throws RentalDataNotFoundException - Checked exception to throw for data issues.
     */
    protected void validateToolCode(String toolCode) throws RentalDataNotFoundException {
        if (ObjectUtils.isEmpty(toolService.getTool(toolCode))) {
            throw new RentalDataNotFoundException("Tool Code doesn't exist with code: " + toolCode);
        }

    }

    /**
     * Method to validate user ids exists in the system.
     * @param customerId - Customer Id
     * @param storeAssociateId - Store Associate Id
     * @throws RentalDataNotFoundException - Checked exception to throw for data issues.
     */
    protected void validateUsers(String customerId, String storeAssociateId) throws RentalDataNotFoundException {
        if (ObjectUtils.isEmpty(userService.getUser(customerId))) {
            throw new RentalDataNotFoundException("Customer doesn't exist with id: " + customerId);
        }

        if (ObjectUtils.isEmpty(userService.getUser(storeAssociateId))) {
            throw new RentalDataNotFoundException("Store Associate doesn't exist with id: " + storeAssociateId);
        }
    }
}
