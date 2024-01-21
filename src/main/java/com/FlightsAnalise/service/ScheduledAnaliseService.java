package com.FlightsAnalise.service;

import com.FlightsAnalise.model.FlightOrder;

public interface ScheduledAnaliseService {
    void addFinalAnalise(FlightOrder flightOrder);

    void addSingleAnalise(FlightOrder flightOrder);
}
