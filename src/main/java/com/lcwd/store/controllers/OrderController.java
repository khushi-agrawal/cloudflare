package com.lcwd.store.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lcwd.store.dtos.CreateOrderRequest;
import com.lcwd.store.dtos.OrderDto;
import com.lcwd.store.services.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

//    private String userId = "bf7ef4ee-89d8-4605-9f2b-61553659f64c";

    // create
    @PostMapping("/{userId}")
    public ResponseEntity<OrderDto> createOrder(
            @RequestBody CreateOrderRequest createOrderData, @PathVariable String userId ) {

        OrderDto createOrder = orderService.createOrder(createOrderData, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createOrder);

    }

}
