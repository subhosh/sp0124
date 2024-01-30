package com.sp.trms.dao;

import com.sp.trms.domain.Price;
import com.sp.trms.domain.ToolType;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Data Access Object to process data requests from service layer.
 */
@Component
public class PriceDao {
    //In Memory Database
    Map<String, Price> prices = Map.ofEntries(
            Map.entry(ToolType.Ladder.name(), new Price("1", ToolType.Ladder, 1.99f, Boolean.FALSE, Boolean.TRUE, Boolean.TRUE)),
            Map.entry(ToolType.Chainsaw.name(), new Price("2", ToolType.Chainsaw, 1.49f, Boolean.TRUE, Boolean.FALSE, Boolean.TRUE)),
            Map.entry(ToolType.Jackhammer.name(), new Price("3", ToolType.Jackhammer, 2.99f, Boolean.FALSE, Boolean.FALSE, Boolean.TRUE))
    );

    /**
     * Get Price details of a tool type.
     * @param type Tool Type
     * @return Price
     */
    public Price getPrice(ToolType type) {
        return prices.get(type.name());
    }
}
