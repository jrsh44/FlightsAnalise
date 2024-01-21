package com.FlightsAnalise.controller;

import com.FlightsAnalise.model.FlightOrder;
import com.FlightsAnalise.model.KiwiOrderBuilder;
import com.FlightsAnalise.model.SingleAnalise;
import com.FlightsAnalise.model.SingleFlight;
import com.FlightsAnalise.service.AnaliseService;
import com.FlightsAnalise.service.FlightService;
import com.FlightsAnalise.service.KiwiService;
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

    @DeleteMapping("orders")
    public ResponseEntity<Void> deleteAllOrders() {
        orderService.delAll();
        return ResponseEntity.ok().build();
    }

    // #################### TO TEST IF IT WORKS #######################

    // Flights repo
    @Autowired
    private FlightService flightService;

    @Autowired
    private KiwiService kiwiService;

    @GetMapping("flights")
    public ResponseEntity<List<SingleFlight>> getAllFlights() {
        return ResponseEntity.ok(flightService.getAll());
    }

    @DeleteMapping("flights/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable(value = "id") Integer id) {
        flightService.delById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("flights")
    public ResponseEntity<Void> deleteAllFlights() {
        flightService.delAll();
        return ResponseEntity.ok().build();
    }

    @PostMapping("flights")
    public ResponseEntity<SingleFlight> addFlight(@RequestBody FlightOrder flightOrder) {
        // For now it gets first (the chepeast) flight
        return ResponseEntity.ok(flightService.add(
                kiwiService.search(new KiwiOrderBuilder(
                                flightOrder.getFlyFrom(),
                                flightOrder.getFlyTo(),
                                flightOrder.getDateFrom(),
                                flightOrder.getDateTo(),
                                flightOrder.getNumOfTests(),
                                flightOrder.getTestTimeGap())
                                .adults(flightOrder.getAdults())
                                .children(flightOrder.getChildren()))
                        .getData().get(0)
        ));
    }

    //Analise repo
    @Autowired
    private AnaliseService analiseService;

    @GetMapping("analyses1")
    public ResponseEntity<List<SingleAnalise>> getAllSingleAnalyses() {
        return ResponseEntity.ok(analiseService.getAllSingleAnalise());
    }

    @DeleteMapping("analyses1")
    public ResponseEntity<Void> deleteAllSingleAnalyses() {
        analiseService.deleteAllSingleAnalise();
        //Delete also this bcs if there are no analyses there shouldn't be any flights in flight repo
        flightService.delAll();
        return ResponseEntity.ok().build();
    }
}

