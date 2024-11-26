package com.jotech.mssc_brewery.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jotech.mssc_brewery.web.model.CustomerDto;
import com.jotech.mssc_brewery.service.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    CustomerDto validCustomerDto;


    @MockBean
    CustomerService customerService;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {

        validCustomerDto = CustomerDto
                .builder()
                .id(UUID.randomUUID())
                .customerName("Jhon Buck")
                .build();

    }

    @Test
    public void getCustomer() throws Exception {

        given(customerService.getCustomerByID(any(UUID.class))).willReturn(validCustomerDto);

        mockMvc.perform(get("/api/v1/customer/"+validCustomerDto.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id",is(validCustomerDto.getId().toString())))
                .andExpect(jsonPath("$.customerName",is("Jhon Buck")));
    }

    @Test
    public void handlePostCustomer() throws Exception {

        //given
        CustomerDto customerDto = validCustomerDto;

        customerDto.setId(UUID.randomUUID());

        CustomerDto savedCustomerDto = CustomerDto
                .builder()
                .id(UUID.randomUUID())
                .customerName("New Customer")
                .build();

        String customerDtoJson = objectMapper.writeValueAsString(customerDto);

        given(customerService.saveNewCustomer(any())).willReturn(savedCustomerDto);

        mockMvc.perform(post("/api/v1/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerDtoJson))
                .andExpect(status().isCreated());

    }

    @Test
    public void handleUpdate() {
    }

    @Test
    public void deleteCustomer() {
    }
}