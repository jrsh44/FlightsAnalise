package com.FlightsAnalise.service.impl;

import com.FlightsAnalise.exceptions.GotNullException;
import com.FlightsAnalise.model.*;
import com.FlightsAnalise.model.receivedJson.Flight;
import com.FlightsAnalise.model.receivedJson.KiwiData;
import com.FlightsAnalise.repository.FinalAnaliseRepository;
import com.FlightsAnalise.repository.SingleAnaliseRepository;
import com.FlightsAnalise.service.AnaliseService;
import com.FlightsAnalise.service.FlightService;
import com.FlightsAnalise.service.KiwiService;
import com.FlightsAnalise.service.scheduler.ScheduledAnalise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class AnaliseServiceImpl implements AnaliseService {

    @Autowired
    private FlightService flightService;

    @Autowired
    private KiwiService kiwiService;

    @Autowired
    private SingleAnaliseRepository singleAnaliseRepository;

    @Autowired
    private FinalAnaliseRepository finalAnaliseRepository;

    @Override
    public void setAnalise(FlightOrder flightOrder) {
        new ScheduledAnalise(flightOrder, this);
    }

    // TODO - FINAL ANALISE
    @Override
    public void addFinalAnalise(FlightOrder flightOrder, Cabin cabin) {

        boolean isCabinExist = singleAnaliseRepository.findAll()
                .stream()
                .filter(s -> s.getFlightOrder().getId() == flightOrder.getId())
                .anyMatch(s -> s.getCabin().equals(cabin.label));

        if (isCabinExist) {
            FinalAnalise finalAnalise = new FinalAnalise();

            //Setting flightOrder
            finalAnalise.setFlightOrder(flightOrder);

            //Setting cabin
            finalAnalise.setCabin(cabin.label);

            //Get first and last analise (Nw czy wgl potrzebny ten throw tu)
            SingleAnalise firstAnalise = singleAnaliseRepository.findAll()
                    .stream()
                    .filter(s -> s.getFlightOrder().getId() == flightOrder.getId())
                    .filter(s -> s.getCabin().equals(cabin.label))
                    .min(Comparator.comparing(SingleAnalise::getCreatedAt))
                    .orElseThrow(() -> new GotNullException("Didn't get any single analise for order:" + flightOrder.getId() + " for cabin:" + cabin));

            SingleAnalise lastAnalise = singleAnaliseRepository.findAll()
                    .stream()
                    .filter(s -> s.getFlightOrder().getId() == flightOrder.getId())
                    .filter(s -> s.getCabin().equals(cabin.label))
                    .max(Comparator.comparing(SingleAnalise::getCreatedAt))
                    .orElseThrow(() -> new GotNullException("Didn't get any single analise for order:" + flightOrder.getId() + " for cabin:" + cabin));

            //Setting start price (Taking from best offer)
            finalAnalise.setStartPrice(firstAnalise.getBestOffer().getPrice());

            //Setting end price (also from best offer)
            finalAnalise.setEndPrice(lastAnalise.getBestOffer().getPrice());

            //Setting averagePrice (niestety nie do końca average, bo to średnia ze średnich)
            //Nie da sie inaczej, bo nie ma polaczenia flights <-> flightOrder (flights z Flights Repository)
            finalAnalise.setAveragePrice(singleAnaliseRepository.findAll()
                    .stream()
                    .filter(s -> s.getFlightOrder().getId() == flightOrder.getId())
                    .filter(s -> s.getCabin().equals(cabin.label))
                    .mapToDouble(SingleAnalise::getAveragePrice)
                    .average()
                    .orElse(0.0));

            //Setting priceChange
            finalAnalise.setPriceChange(finalAnalise.getEndPrice() - finalAnalise.getStartPrice());

            //Setting startAmount
            finalAnalise.setStartAmount(firstAnalise.getAmount());

            //Setting endAmount
            finalAnalise.setEndAmount(lastAnalise.getAmount());

            //Setting amountChange
            finalAnalise.setAmountChange(finalAnalise.getEndAmount() - finalAnalise.getStartAmount());

            //Setting bestOffer
            finalAnalise.setBestOffer(singleAnaliseRepository.findAll()
                    .stream()
                    .filter(s -> s.getFlightOrder().getId() == flightOrder.getId())
                    .filter(s -> s.getCabin().equals(cabin.label))
                    .map(SingleAnalise::getBestOffer)
                    .min(Comparator.comparing(SingleFlight::getPrice))
                    .get());

            //Setting worstOffer
            finalAnalise.setWorstOffer(singleAnaliseRepository.findAll()
                    .stream()
                    .filter(s -> s.getFlightOrder().getId() == flightOrder.getId())
                    .filter(s -> s.getCabin().equals(cabin.label))
                    .map(SingleAnalise::getWorstOffer)
                    .max(Comparator.comparing(SingleFlight::getPrice))
                    .get());

            finalAnaliseRepository.save(finalAnalise);
        }
    }

    @Override
    public void addSingleAnalise(FlightOrder flightOrder, Cabin cabin) {

        KiwiData kiwiData = kiwiService.search(new KiwiOrderBuilder(
                flightOrder.getFlyFrom(),
                flightOrder.getFlyTo(),
                flightOrder.getDateFrom(),
                flightOrder.getDateTo(),
                flightOrder.getNumOfTests(),
                flightOrder.getTestTimeGap())
                .adults(flightOrder.getAdults())
                .children(flightOrder.getChildren())
                .cabin(cabin));

        if (!kiwiData.getData().isEmpty()) {

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

    // TODO - remove
    @Override
    public List<FinalAnalise> getAllFinalAnalise() {
        return finalAnaliseRepository.findAll();
    }

    // TODO - remove
    @Override
    public void deleteAllFinalAnalise() {
        finalAnaliseRepository.deleteAll();
    }
}

