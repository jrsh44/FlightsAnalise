package com.FlightsAnalise.service;

import com.FlightsAnalise.model.SingleFlight;
import com.FlightsAnalise.model.receivedJson.Flight;
import com.FlightsAnalise.model.receivedJson.KiwiData;

import java.util.List;

public interface FlightService {
    List<SingleFlight> getAll();

    SingleFlight getById(int id);

    SingleFlight add(Flight flight);

    void delById(int id);

    void delAll();
}
