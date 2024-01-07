package com.FlightsAnalise.service;

import com.FlightsAnalise.model.FlightOrder;

import java.util.List;

public interface OrderService {
    FlightOrder add(FlightOrder flightOrder);

    List<FlightOrder> getAll();

    FlightOrder getById(int id);

    void delById(int id);

    void delAll();
}

