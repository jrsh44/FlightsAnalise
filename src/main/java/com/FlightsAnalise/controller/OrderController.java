package com.FlightsAnalise.controller;

import com.FlightsAnalise.model.FlightOrder;
import com.FlightsAnalise.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("orders")
    public ResponseEntity<FlightOrder> addOrder(@RequestBody FlightOrder flightOrder) {
        return ResponseEntity.ok(orderService.add(flightOrder));
    }

    @GetMapping("orders")
    public ResponseEntity<List<FlightOrder>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @GetMapping("orders/{id}")
    public ResponseEntity<FlightOrder> getOrder(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok().body(orderService.getById(id));
    }

    @DeleteMapping("orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable(value = "id") Integer id) {
        orderService.delById(id);
        return ResponseEntity.ok().build();
    }


}

