package com.distributed.order.controller;

import com.distributed.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping ("/{orderId}")
    public ResponseEntity<String> createOrder(@PathVariable String orderId) {
        log.info("Received create-order request for orderId={}", orderId);
        String result = orderService.createOrder(orderId);
        log.info("Completed create-order request for orderId={}", orderId);
        return ResponseEntity.ok(result);
    }
}
