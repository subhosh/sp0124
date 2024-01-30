package com.sp.trms.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tool {
    private String id;
    private String code;
    private ToolType type;
    private ToolBrand brand;
}
