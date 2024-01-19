package com.FlightsAnalise.service;

import com.FlightsAnalise.model.FinalAnalise;
import com.FlightsAnalise.model.FlightOrder;
import com.FlightsAnalise.model.SingleAnalise;

import java.util.List;

public interface AnaliseService extends ScheduledAnaliseService {
    List<SingleAnalise> getAllSingleAnalise();

    void setAnalise(FlightOrder flightOrder);

    void deleteAllSingleAnalise();

    List<FinalAnalise> getAllFinalAnalise();

    void deleteAllFinalAnalise();

}
