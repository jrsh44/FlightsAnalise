package com.FlightsAnalise.service.impl;

import com.FlightsAnalise.model.FlightOrder;
import com.FlightsAnalise.model.KiwiOrderBuilder;
import com.FlightsAnalise.model.SingleAnalise;
import com.FlightsAnalise.model.receivedJson.Flight;
import com.FlightsAnalise.model.receivedJson.KiwiData;
import com.FlightsAnalise.repository.SingleAnaliseRepository;
import com.FlightsAnalise.service.AnaliseService;
import com.FlightsAnalise.service.FlightService;
import com.FlightsAnalise.service.KiwiService;
import com.FlightsAnalise.service.scheduler.ScheduledAnalise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class AnaliseServiceImpl implements AnaliseService {

    @Autowired
    private FlightService flightService;

    @Autowired
    private KiwiService kiwiService;

    @Autowired
    private SingleAnaliseRepository singleAnaliseRepository;

    @Override
    public void setAnalise(FlightOrder flightOrder) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
        scheduler.scheduleAtFixedRate(new ScheduledAnalise(flightOrder, this), 0, flightOrder.getTestTimeGap(), TimeUnit.MINUTES);
        scheduler.schedule(scheduler::shutdown, (long) flightOrder.getTestTimeGap() * flightOrder.getNumOfTests(), TimeUnit.MINUTES);
    }

    // TODO - FINAL ANALISE
    @Override
    public void addFinalAnalise(FlightOrder flightOrder) {
        System.out.println("FINAL ANALISE");
    }

    @Override
    public void addSingleAnalise(FlightOrder flightOrder) {

        KiwiData kiwiData = kiwiService.search(new KiwiOrderBuilder(
                flightOrder.getFlyFrom(),
                flightOrder.getFlyTo(),
                flightOrder.getDateFrom(),
                flightOrder.getDateTo(),
                flightOrder.getNumOfTests(),
                flightOrder.getTestTimeGap())
                .adults(flightOrder.getAdults())
                .children(flightOrder.getChildren()));

        SingleAnalise singleAnalise = new SingleAnalise();
        singleAnalise.setFlightOrder(flightOrder);

        //Calculate average price
        singleAnalise.setAveragePrice(kiwiData.getData().stream()
                .mapToDouble(Flight::getPrice)
                .average()
                .orElse(0.0));

        //Idk maybe just for now like that
        singleAnalise.setCabin(kiwiData.getData()
                .get(0).getRoute().get(0)
                .getFareCategory());

        singleAnalise.setAmount(kiwiData.getResults());

        //Offers in KiwiData are sorted so we can just take first and last offer
        singleAnalise.setBestOffer(
                flightService.add(
                        kiwiData.getData().get(0)
                )
        );
        singleAnalise.setWorstOffer(
                flightService.add(
                        kiwiData.getData().get(kiwiData.getData().size() - 1)
                )
        );
        singleAnaliseRepository.save(singleAnalise);
    }

    // TODO - remove
    @Override
    public List<SingleAnalise> getAllSingleAnalise() {
        return singleAnaliseRepository.findAll();
    }

    // TODO - remove
    @Override
    public void deleteAllSingleAnalise() {
        singleAnaliseRepository.deleteAll();
    }
}

