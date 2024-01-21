package com.FlightsAnalise.service;

import com.FlightsAnalise.model.Cabin;
import com.FlightsAnalise.model.FlightOrder;

public interface ScheduledAnaliseService {
    void addFinalAnalise(FlightOrder flightOrder, Cabin cabin);

    void addSingleAnalise(FlightOrder flightOrder, Cabin cabin);

    void addReport(FlightOrder flightOrder);
}
