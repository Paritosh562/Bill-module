package org.example.service;

import org.example.dto.UserBillRequest;
import org.example.service.impl.AffiliateDiscountStrategy;
import org.example.service.impl.BillingServiceImpl;
import org.example.service.impl.CustomerOverTwoYearsDiscountStrategy;
import org.example.service.impl.EmployeeDiscountStrategy;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BillingServiceTest {
    @InjectMocks
    private BillingServiceImpl billingService;

    @Test
    public void testCalculateNetPayableAmount_employeeWithGroceries() {
        UserBillRequest bill = new UserBillRequest();
        bill.setAmount(100);
        bill.setIsEmployee(true);
        bill.setIsGroceries(true);

        chooseStrategy(bill);

        double netPayableAmount = billingService.calculateNetPayableAmount(bill);

        assertEquals(95, netPayableAmount, 0);
    }

    @Test
    public void testCalculateNetPayableAmount_employeeWithoutGroceries() {
        UserBillRequest bill = new UserBillRequest();
        bill.setAmount(100);
        bill.setIsEmployee(true);
        bill.setIsGroceries(false);
        chooseStrategy(bill);
        double netPayableAmount = billingService.calculateNetPayableAmount(bill);

        assertEquals(70, netPayableAmount, 0);
    }

    @Test
    public void testCalculateNetPayableAmount_affiliateWithGroceries() {
        UserBillRequest bill = new UserBillRequest();
        bill.setAmount(100);
        bill.setIsAffiliate(true);
        bill.setIsGroceries(true);
        chooseStrategy(bill);
        double netPayableAmount = billingService.calculateNetPayableAmount(bill);

        assertEquals(95, netPayableAmount, 0);
    }

    @Test
    public void testCalculateNetPayableAmount_affiliateWithoutGroceries() {
        UserBillRequest bill = new UserBillRequest();
        bill.setAmount(100);
        bill.setIsAffiliate(true);
        bill.setIsGroceries(false);
        chooseStrategy(bill);
        double netPayableAmount = billingService.calculateNetPayableAmount(bill);

        assertEquals(90, netPayableAmount, 0);
    }

    @Test
    public void testCalculateNetPayableAmount_customerOverTwoYearsWithGroceries() {
        UserBillRequest bill = new UserBillRequest();
        bill.setAmount(100);
        bill.setIsCustomerOverTwoYears(true);
        bill.setIsGroceries(true);
        chooseStrategy(bill);
        double netPayableAmount = billingService.calculateNetPayableAmount(bill);

        assertEquals(95, netPayableAmount, 0);
    }

    @Test
    public void testCalculateNetPayableAmount_customerOverTwoYearsWithoutGroceries() {
        UserBillRequest bill = new UserBillRequest();
        bill.setAmount(100);
        bill.setIsCustomerOverTwoYears(true);
        bill.setIsGroceries(false);
        chooseStrategy(bill);
        double netPayableAmount = billingService.calculateNetPayableAmount(bill);

        assertEquals(95, netPayableAmount, 0);
    }

    @Test
    public void testCalculateNetPayableAmount_regularCustomerWithGroceries() {
        UserBillRequest bill = new UserBillRequest();
        bill.setAmount(100);
        bill.setIsCustomerOverTwoYears(false);
        bill.setIsGroceries(true);
        chooseStrategy(bill);
        double netPayableAmount = billingService.calculateNetPayableAmount(bill);

        assertEquals(95, netPayableAmount, 0);
    }

    @Test
    public void testCalculateNetPayableAmount_regularCustomerWithoutGroceries() {
        UserBillRequest bill = new UserBillRequest();
        bill.setAmount(100);
        bill.setIsCustomerOverTwoYears(false);
        bill.setIsGroceries(false);
        chooseStrategy(bill);
        double netPayableAmount = billingService.calculateNetPayableAmount(bill);

        assertEquals(95, netPayableAmount, 0);
    }

    @Test
    public void testCalculateNetPayableAmount_multipleOf100Bill() {
        UserBillRequest bill = new UserBillRequest();
        bill.setAmount(1000);
        bill.setIsEmployee(true);
        bill.setIsGroceries(false);
        chooseStrategy(bill);
        double netPayableAmount = billingService.calculateNetPayableAmount(bill);

        assertEquals(665, netPayableAmount, 0);
    }

    private void chooseStrategy(UserBillRequest bill) {
        DiscountStrategy discountStrategy = null;

        if (bill.getIsEmployee()) {
            discountStrategy = new EmployeeDiscountStrategy();
        } else if (bill.getIsAffiliate()) {
            discountStrategy = new AffiliateDiscountStrategy();
        } else if (bill.getIsCustomerOverTwoYears()) {
            discountStrategy = new CustomerOverTwoYearsDiscountStrategy();
        }

        billingService.setDiscountStrategy(discountStrategy);
    }
}
