package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.StringResource;
import org.example.dto.UserBillResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BillingControllerTest {

    @Autowired
    private MockMvc mvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void calculateNetPayableAmountEmployee(@StringResource("classpath:bill_request_employee.json") String request) throws Exception {

        MvcResult mvcResult = mvc.perform(post("/bill")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful()).andReturn();
        UserBillResponse userBillResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), UserBillResponse.class);
        assertEquals(665, userBillResponse.getNetPayableAmount());

    }

    @Test
    void calculateNetPayableAmountGroceries(@StringResource("classpath:bill_request_groceries.json") String request) throws Exception {

        MvcResult mvcResult = mvc.perform(post("/bill")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful()).andReturn();
        UserBillResponse userBillResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), UserBillResponse.class);
        assertEquals(950, userBillResponse.getNetPayableAmount());

    }

    @Test
    void calculateNetPayableAmountAffiliate(@StringResource("classpath:bill_request_affiliate.json") String request) throws Exception {

        MvcResult mvcResult = mvc.perform(post("/bill")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful()).andReturn();
        UserBillResponse userBillResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), UserBillResponse.class);
        assertEquals(855, userBillResponse.getNetPayableAmount());

    }

    @Test
    void calculateNetPayableAmountCustOverTwoYears(@StringResource("classpath:bill_request_customer_over_2_years.json") String request) throws Exception {

        MvcResult mvcResult = mvc.perform(post("/bill")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful()).andReturn();
        UserBillResponse userBillResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), UserBillResponse.class);
        assertEquals(905, userBillResponse.getNetPayableAmount());

    }
}