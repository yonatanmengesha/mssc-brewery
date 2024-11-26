package com.jotech.mssc_brewery.service;

import com.jotech.mssc_brewery.web.model.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    @Override
    public CustomerDto getCustomerByID(UUID customerID) {
        return CustomerDto.builder().id(UUID.randomUUID())
                .customerName("Jhon Z").build();
    }

    @Override
    public CustomerDto saveNewCustomer(CustomerDto customerDto) {
        return CustomerDto.
                builder().id(UUID.randomUUID()).customerName("Jhon M")
                .build();
    }

    @Override
    public void updateCustomer(UUID customerId, CustomerDto customerDto) {

        //todo to be implemented

        log.debug("Updating Customer ...");
    }

    @Override
    public void deleteCustomerById(UUID customerId) {

        log.debug("Deleting Customer by Id ...");
    }
}
