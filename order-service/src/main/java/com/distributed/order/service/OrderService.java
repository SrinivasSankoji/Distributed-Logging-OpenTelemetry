package com.distributed.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final RestTemplate restTemplate;

    public String createOrder(String orderId) {
        log.info("Calling Service B for orderId={}", orderId);
        String response = restTemplate.postForObject(
                "http://localhost:8082/customer/validate/" + orderId, null, String.class);
        log.info("Received response from Service B for orderId={}", orderId);
        return "A -> " + response;
    }
}
