package org.example.service.impl;

import org.example.dto.UserBillRequest;
import org.example.service.BillingService;
import org.example.service.DiscountStrategy;
import org.springframework.stereotype.Service;

@Service
public class BillingServiceImpl implements BillingService {
    private DiscountStrategy discountStrategy;

    @Override
    public void setDiscountStrategy(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    @Override
    public DiscountStrategy getDiscountStrategy() {
        return this.discountStrategy;
    }

    public double calculateNetPayableAmount(UserBillRequest bill) {
        double netPayableAmount = bill.getAmount();

        if (!bill.getIsGroceries() && discountStrategy != null) {
            netPayableAmount -= discountStrategy.calculateDiscount(netPayableAmount);
        }

        netPayableAmount -= calculateBillDiscount(netPayableAmount);

        return netPayableAmount;
    }

    private double calculateBillDiscount(double amount) {
        return Math.floor(amount / 100) * 5;
    }
}
