package com.sp.trms.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Store {
    private String id;

    private List<Discount> discounts;

    private Catalog catalog;

    private List<Holiday> holidays;
}
