package com.jotech.mssc_brewery.web.controller;


import com.jotech.mssc_brewery.web.model.CustomerDto;
import com.jotech.mssc_brewery.service.CustomerService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("api/v1/customer")
@RestController
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable("customerId") UUID customerId){


        return   new ResponseEntity<>( customerService.getCustomerByID(customerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity   handlePostCustomer(@RequestBody  CustomerDto customerDto){

              CustomerDto savedCustomerDto =  customerService.saveNewCustomer(customerDto);

              HttpHeaders headers = new HttpHeaders();

              headers.add("Location","/api/v1/customer/"+ savedCustomerDto.getId().toString());

              return new ResponseEntity(headers,HttpStatus.CREATED);

    }

    @PutMapping("/{customerId}")
    public ResponseEntity   handleUpdate(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDto customerDto){

        customerService.updateCustomer(customerId,customerDto);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable("customerId")  UUID customerId){

        customerService.deleteCustomerById(customerId);



    }
}
