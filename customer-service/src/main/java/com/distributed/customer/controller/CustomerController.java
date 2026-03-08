package com.distributed.customer.controller;

import com.distributed.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/customer")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping ("/validate/{orderId}")
    public String validateCustomer(@PathVariable String orderId) {
        log.info("Entered customer validation for orderId={}", orderId);
        String result = customerService.validate(orderId);
        log.info("Customer validation completed for orderId={}", orderId);
        return result;
    }
}
