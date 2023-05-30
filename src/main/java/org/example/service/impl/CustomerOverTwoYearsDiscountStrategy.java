package org.example.service.impl;

import org.example.service.DiscountStrategy;

public class CustomerOverTwoYearsDiscountStrategy implements DiscountStrategy {
    private static final double CUSTOMER_OVER_TWO_YEARS_DISCOUNT_PERCENTAGE = 0.05;

    @Override
    public double calculateDiscount(double amount) {
        return amount * CUSTOMER_OVER_TWO_YEARS_DISCOUNT_PERCENTAGE;
    }
}