package com.sp.trms.dao;

import com.sp.trms.domain.Tool;
import com.sp.trms.domain.ToolBrand;
import com.sp.trms.domain.ToolType;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ToolDao {
    //In Memory Database
    Map<String, Tool> tools = Map.ofEntries(
      Map.entry("CHNS", new Tool("1", "CHNS", ToolType.Chainsaw, ToolBrand.Stihl)),
      Map.entry("LADW", new Tool("2", "LADW", ToolType.Ladder, ToolBrand.Werner)),
      Map.entry("JAKD", new Tool("3", "JAKD", ToolType.Jackhammer, ToolBrand.DeWalt)),
      Map.entry("JAKR", new Tool("4", "JAKR", ToolType.Jackhammer, ToolBrand.Ridgid))
    );

    public Tool getTool(String code) {
        return tools.get(code);
    }
}
