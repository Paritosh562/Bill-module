package org.example.service;

import org.example.dto.UserBillRequest;

public interface BillingService {
    double calculateNetPayableAmount(UserBillRequest bill);

    void setDiscountStrategy(DiscountStrategy discountStrategy);

    DiscountStrategy getDiscountStrategy();
}
