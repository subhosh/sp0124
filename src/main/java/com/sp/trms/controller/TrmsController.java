package com.sp.trms.controller;

import com.sp.trms.exception.RentalDataNotFoundException;
import com.sp.trms.rest.model.CheckoutRequest;
import com.sp.trms.rest.model.CheckoutResponse;
import com.sp.trms.service.TrmsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 */
@Controller
public class TrmsController {

    private final TrmsService trmsService;

    @Autowired
    public TrmsController(TrmsService trmsService) {
        this.trmsService = trmsService;
    }

    @PostMapping(path="/trms/checkout")
    public ResponseEntity<CheckoutResponse> checkout(@RequestBody @Valid CheckoutRequest checkoutRequest)
            throws RentalDataNotFoundException {
        //Validate the request (business validations)
        this.trmsService.validateCheckoutRequest(checkoutRequest);

        //Process checkout request
        return ResponseEntity.ok(this.trmsService.checkout(checkoutRequest));
    }
}
