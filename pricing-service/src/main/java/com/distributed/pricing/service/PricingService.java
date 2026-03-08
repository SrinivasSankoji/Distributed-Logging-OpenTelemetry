package com.distributed.pricing.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class PricingService {

    private final RestTemplate restTemplate;

    public String calculate(String orderId) {
        log.info("Price calculated for orderId={}", orderId);
        log.info("Calling Service D for orderId={}", orderId);

        String response = restTemplate.postForObject(
                "http://localhost:8084/inventory/reserve/" + orderId,
                null,
                String.class
        );

        log.info("Received response from Service D for orderId={}", orderId);
        return "C -> " + response;
    }
}
