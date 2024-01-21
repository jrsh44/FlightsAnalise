package com.FlightsAnalise.service.impl;

import com.FlightsAnalise.exceptions.ResourceNotFoundException;
import com.FlightsAnalise.model.*;
import com.FlightsAnalise.model.receivedJson.Flight;
import com.FlightsAnalise.model.receivedJson.KiwiData;
import com.FlightsAnalise.repository.FinalAnaliseRepository;
import com.FlightsAnalise.repository.ReportRepository;
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

    @Autowired
    private ReportRepository reportRepository;

    @Override
    public void setAnalise(FlightOrder flightOrder) {
        new ScheduledAnalise(flightOrder, this);
    }

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

            //Get first and last analise
            SingleAnalise firstAnalise = singleAnaliseRepository.findAll()
                    .stream()
                    .filter(s -> s.getFlightOrder().getId() == flightOrder.getId())
                    .filter(s -> s.getCabin().equals(cabin.label))
                    .min(Comparator.comparing(SingleAnalise::getCreatedAt))
                    .get();

            SingleAnalise lastAnalise = singleAnaliseRepository.findAll()
                    .stream()
                    .filter(s -> s.getFlightOrder().getId() == flightOrder.getId())
                    .filter(s -> s.getCabin().equals(cabin.label))
                    .max(Comparator.comparing(SingleAnalise::getCreatedAt))
                    .get();

            //Setting start price (Taking from best offer)
            finalAnalise.setStartPrice(firstAnalise.getBestOffer().getPrice());

            //Setting end price (also from best offer)
            finalAnalise.setEndPrice(lastAnalise.getBestOffer().getPrice());

            //Setting averagePrice
            int amountsSum = singleAnaliseRepository.findAll().stream()
                    .filter(s -> s.getFlightOrder().getId() == flightOrder.getId())
                    .filter(s -> s.getCabin().equals(cabin.label))
                    .mapToInt(SingleAnalise::getAmount)
                    .sum();

            finalAnalise.setAveragePrice(singleAnaliseRepository.findAll()
                    .stream()
                    .filter(s -> s.getFlightOrder().getId() == flightOrder.getId())
                    .filter(s -> s.getCabin().equals(cabin.label))
                    .mapToDouble(s -> s.getAveragePrice() * s.getAmount())
                    .sum() / (double) amountsSum);

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

            //Set Cabin
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

    @Override
    public List<SingleAnalise> getAllSingleAnalise() {
        return singleAnaliseRepository.findAll();
    }

    @Override
    public void deleteAllSingleAnalise() {
        singleAnaliseRepository.deleteAll();
    }

    @Override
    public List<FinalAnalise> getAllFinalAnalise() {
        return finalAnaliseRepository.findAll();
    }

    @Override
    public void deleteAllFinalAnalise() {
        finalAnaliseRepository.deleteAll();
    }


    @Override
    public SingleAnalise getSingleAnaliseById(int id) {
        return singleAnaliseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't find single analise with ID = " + id));
    }

    @Override
    public void deleteSingleAnaliseById(int id) {
        SingleFlight best = getSingleAnaliseById(id).getBestOffer();
        SingleFlight worst = getSingleAnaliseById(id).getWorstOffer();

        singleAnaliseRepository.delete(getSingleAnaliseById(id));
        flightService.delById(best.getId());
        flightService.delById(worst.getId());
    }

    @Override
    public FinalAnalise getFinalAnaliseById(int id) {
        return finalAnaliseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't find final analise with ID = " + id));
    }

    @Override
    public void deleteFinalAnaliseById(int id) {
        finalAnaliseRepository.delete(getFinalAnaliseById(id));
    }

    @Override
    public void addReport(FlightOrder flightOrder) {
        Report report = new Report();

        //Get all final analyses done for flightOrder
        List<FinalAnalise> finalAnalyses = getAllFinalAnalise().stream()
                .filter(s -> s.getFlightOrder().getId() == flightOrder.getId())
                .toList();

        //Get all single analyses done for flightOrder
        List<SingleAnalise> singleAnalyses = getAllSingleAnalise().stream()
                .filter(s -> s.getFlightOrder().getId() == flightOrder.getId())
                .toList();

        //Setting flightOrder
        report.setFlightOrder(flightOrder);

        //Setting id to same as flightOrder
        report.setId(flightOrder.getId());

        //Setting start and end price
        report.setStartPrice(finalAnalyses.stream()
                .mapToDouble(FinalAnalise::getStartPrice)
                .min().getAsDouble());

        report.setEndPrice(finalAnalyses.stream()
                .mapToDouble(FinalAnalise::getEndPrice)
                .min().getAsDouble());

        //Setting averagePrice
        int amountsSum = singleAnalyses.stream()
                .mapToInt(SingleAnalise::getAmount)
                .sum();

        report.setAveragePrice(singleAnalyses.stream()
                .mapToDouble(s -> s.getAveragePrice() * s.getAmount())
                .sum() / (double) amountsSum);

        //Setting priceChange
        report.setPriceChange(report.getEndPrice() - report.getStartPrice());

        //Setting startAmount
        report.setStartAmount(finalAnalyses.stream()
                .mapToInt(FinalAnalise::getStartAmount)
                .sum());

        //Setting endAmount
        report.setEndAmount(finalAnalyses.stream()
                .mapToInt(FinalAnalise::getEndAmount)
                .sum());

        //Setting amountChange
        report.setAmountChange(report.getEndAmount() - report.getStartAmount());

        //Setting finalAnalyses
        report.setEconomyAnalise(finalAnalyses.stream()
                .filter(s -> s.getCabin().equals(Cabin.ECONOMY.label))
                .findAny().orElse(null));

        report.setEconomyPremiumAnalise(finalAnalyses.stream()
                .filter(s -> s.getCabin().equals(Cabin.PREMIUM_ECONOMY.label))
                .findAny().orElse(null));

        report.setBusinessAnalise(finalAnalyses.stream()
                .filter(s -> s.getCabin().equals(Cabin.BUSINESS.label))
                .findAny().orElse(null));

        report.setFirstClassAnalise(finalAnalyses.stream()
                .filter(s -> s.getCabin().equals(Cabin.FIRST_CLASS.label))
                .findAny().orElse(null));

        reportRepository.save(report);
    }

}

