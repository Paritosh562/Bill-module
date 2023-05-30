package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserBillRequest {
    private double amount;
    private Boolean isEmployee = false;
    private Boolean isAffiliate = false;
    private Boolean isCustomerOverTwoYears = false;
    private Boolean isGroceries = false;
}
