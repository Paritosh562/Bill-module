package org.example.service.impl;

import org.example.service.DiscountStrategy;

public class AffiliateDiscountStrategy implements DiscountStrategy {
    private static final double AFFILIATE_DISCOUNT_PERCENTAGE = 0.1;

    @Override
    public double calculateDiscount(double amount) {
        return amount * AFFILIATE_DISCOUNT_PERCENTAGE;
    }
}