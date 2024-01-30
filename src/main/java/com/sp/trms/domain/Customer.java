package com.sp.trms.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Customer extends User {
    private String cartId;
    private List<RentalAgreement> agreements;
}
