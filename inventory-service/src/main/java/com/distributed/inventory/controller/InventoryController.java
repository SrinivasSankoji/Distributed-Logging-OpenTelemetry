package com.distributed.inventory.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/inventory")
@Slf4j
public class InventoryController {

    @PostMapping ("/reserve/{orderId}")
    public String reserveInventory(@PathVariable String orderId) {
        log.info("Inventory reserved for orderId={}", orderId);
        return "D completed successfully";
    }
}
