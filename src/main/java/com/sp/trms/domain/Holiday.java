package com.sp.trms.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Holiday {
    private String id;
    private String name;
    private LocalDate date;
}
