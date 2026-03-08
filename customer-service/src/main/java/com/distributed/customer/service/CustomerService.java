package com.distributed.customer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {
    private final RestTemplate restTemplate;

    public String validate(String orderId) {
        log.info("Customer is valid for orderId={}", orderId);
        log.info("Calling Service C for orderId={}", orderId);

        String response = restTemplate.postForObject(
                "http://localhost:8083/pricing/calculate/" + orderId,
                null,
                String.class
        );

        log.info("Received response from Service C for orderId={}", orderId);
        return "B -> " + response;
    }
}
