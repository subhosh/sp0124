package com.sp.trms.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Price {
    private String id;
    private ToolType toolType;
    private Float dailyCharge;
    private Boolean isHolidayCharge;
    private Boolean isWeekEndCharge;
    private Boolean isWeekDayCharge;
}
