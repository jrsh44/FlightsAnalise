package com.FlightsAnalise.service.impl;

import com.FlightsAnalise.exceptions.ResourceNotFoundException;
import com.FlightsAnalise.model.SingleFlight;
import com.FlightsAnalise.model.receivedJson.Flight;
import com.FlightsAnalise.repository.FlightRepository;
import com.FlightsAnalise.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightRepository flightRepository;


    @Override
    public List<SingleFlight> getAll() {
        return flightRepository.findAll();
    }

    @Override
    public SingleFlight getById(int id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't find flight with ID = " + id));
    }

    @Override
    public SingleFlight add(Flight flight) {
        SingleFlight singleFlight = new SingleFlight();
        singleFlight.setUtcDeparture(flight.getUtcDeparture().toString());

        //"Arriving" flight arrival time (get last flight from route)
        singleFlight.setUtcArrival(flight.getRoute()
                .get(flight.getRoute().size() - 1)
                .getUtcArrival().toString());
        singleFlight.setNightsInDest(flight.getNightsInDest());
        singleFlight.setPrice(flight.getPrice());

        //Flight duration (both directions) in secs
        singleFlight.setDuration(flight.getDuration().getTotal());
        return flightRepository.save(singleFlight);
    }

    @Override
    public void delById(int id) {
        flightRepository.delete(getById(id));
    }

    @Override
    public void delAll(){
        flightRepository.deleteAll();
    }
}
