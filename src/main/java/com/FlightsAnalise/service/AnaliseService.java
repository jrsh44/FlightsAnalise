package com.FlightsAnalise.service;

import com.FlightsAnalise.model.FlightOrder;
import com.FlightsAnalise.model.SingleAnalise;
import com.FlightsAnalise.model.receivedJson.KiwiData;

import java.util.List;

public interface AnaliseService {
    List<SingleAnalise> getAllSingleAnalise();

    SingleAnalise addSingleAnalise(FlightOrder flightOrder, KiwiData kiwiData);

    void deleteAllSingleAnalise();


}
