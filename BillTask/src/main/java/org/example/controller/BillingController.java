package org.example.controller;

import org.example.dto.UserBillRequest;
import org.example.dto.UserBillResponse;
import org.example.service.BillingService;
import org.example.service.DiscountStrategy;
import org.example.service.impl.AffiliateDiscountStrategy;
import org.example.service.impl.CustomerOverTwoYearsDiscountStrategy;
import org.example.service.impl.EmployeeDiscountStrategy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillingController {
    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @PostMapping("/bill")
    public ResponseEntity<UserBillResponse> calculateNetPayableAmount(@RequestBody UserBillRequest bill) {
        DiscountStrategy discountStrategy = null;

        if (bill.getIsEmployee()) {
            discountStrategy = new EmployeeDiscountStrategy();
        } else if (bill.getIsAffiliate()) {
            discountStrategy = new AffiliateDiscountStrategy();
        } else if (bill.getIsCustomerOverTwoYears()) {
            discountStrategy = new CustomerOverTwoYearsDiscountStrategy();
        }

        billingService.setDiscountStrategy(discountStrategy);
        UserBillResponse userBillResponse = new UserBillResponse();
        userBillResponse.setNetPayableAmount(billingService.calculateNetPayableAmount(bill));
        return ResponseEntity.ok(userBillResponse);
    }
}
