package com.distributed.pricing.controller;

import com.distributed.pricing.service.PricingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/pricing")
@RequiredArgsConstructor
@Slf4j
public class PricingController {

    private final PricingService pricingService;

    @PostMapping ("/calculate/{orderId}")
    public String calculatePrice(@PathVariable String orderId) {
        log.info("Entered pricing calculation for orderId={}", orderId);
        String result = pricingService.calculate(orderId);
        log.info("Pricing calculation completed for orderId={}", orderId);
        return result;
    }
}
