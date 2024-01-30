package com.sp.trms.service;

import com.sp.trms.dao.PriceDao;
import com.sp.trms.domain.Price;
import com.sp.trms.domain.ToolType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Pricing Service to determine perform various tasks related to tools and pricing.
 */
@Service
public class PricingService {
    private final PriceDao priceDao;

    public PricingService(@Autowired PriceDao priceDao) {
        this.priceDao = priceDao;
    }

    /**
     * Get pricing config for a tool type.
     * @param type - Tool Type (Refer to enum @{@link ToolType})
     * @return Price object with config.
     */
    public Price getToolPrice(ToolType type) {
        return priceDao.getPrice(type);
    }

    /**
     * Method to round given amount according to rounding method chosen i.e. half up.
     * @param amount - Amount to be rounded.
     * @return - Rounded value.
     */
    public float round(float amount) {
        BigDecimal amtDecimal = new BigDecimal(amount);
        amtDecimal = amtDecimal.setScale(2, RoundingMode.HALF_UP);
        return amtDecimal.floatValue();
    }
}
