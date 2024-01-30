package com.sp.trms.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StoreAssociate extends User {
    private String employeeId;

    private String homeStore;

    private String department;

    private List<String> permissions;
}
