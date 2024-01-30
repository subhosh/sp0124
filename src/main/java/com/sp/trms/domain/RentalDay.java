package com.sp.trms.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class RentalDay {
    private String id;
    private DayType dayType;
    private LocalDate date;
}
