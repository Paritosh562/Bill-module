package org.example.service.impl;

import org.example.service.DiscountStrategy;

public class EmployeeDiscountStrategy implements DiscountStrategy {
    private static final double EMPLOYEE_DISCOUNT_PERCENTAGE = 0.3;

    @Override
    public double calculateDiscount(double amount) {
        return amount * EMPLOYEE_DISCOUNT_PERCENTAGE;
    }
}


