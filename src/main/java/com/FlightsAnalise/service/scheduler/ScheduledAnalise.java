package com.FlightsAnalise.service.scheduler;

import com.FlightsAnalise.exceptions.GotNullException;
import com.FlightsAnalise.model.Cabin;
import com.FlightsAnalise.model.FlightOrder;
import com.FlightsAnalise.service.ScheduledAnaliseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduledAnalise implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledAnalise.class);

    private final FlightOrder flightOrder;
    private final ScheduledAnaliseService scheduledAnaliseService;
    protected int testsCounter;

    public ScheduledAnalise(FlightOrder flightOrder, ScheduledAnaliseService scheduledAnaliseService) {
        this.flightOrder = flightOrder;
        this.scheduledAnaliseService = scheduledAnaliseService;
    }

    @Override
    public void run() {
        testsCounter++;
        if (testsCounter < flightOrder.getNumOfTests()) {

            scheduledAnaliseService.addSingleAnalise(flightOrder, Cabin.ECONOMY);
            logger.info("Analise nr {} for order {} is completed (ECONOMY CLASS)", testsCounter, flightOrder.getId());

            scheduledAnaliseService.addSingleAnalise(flightOrder, Cabin.PREMIUM_ECONOMY);
            logger.info("Analise nr {} for order {} is completed (PREMIUM_ECONOMY CLASS)", testsCounter, flightOrder.getId());

            scheduledAnaliseService.addSingleAnalise(flightOrder, Cabin.BUSINESS);
            logger.info("Analise nr {} for order {} is completed (BUSINESS CLASS)", testsCounter, flightOrder.getId());

            scheduledAnaliseService.addSingleAnalise(flightOrder, Cabin.FIRST_CLASS);
            logger.info("Analise nr {} for order {} is completed (FIRST CLASS)", testsCounter, flightOrder.getId());
        }
        if (testsCounter == flightOrder.getNumOfTests()) {
            //For now, it only prints stack trace
            try {
                scheduledAnaliseService.addFinalAnalise(flightOrder, Cabin.ECONOMY);
                logger.info("Final analise for order {} is completed (ECONOMY CLASS)", flightOrder.getId());
            } catch (GotNullException ex){
                ex.printStackTrace();
            }

            try {
                scheduledAnaliseService.addFinalAnalise(flightOrder, Cabin.PREMIUM_ECONOMY);
                logger.info("Final analise for order {} is completed (PREMIUM_ECONOMY CLASS)", flightOrder.getId());
            } catch (GotNullException ex){
                ex.printStackTrace();
            }

            try {
                scheduledAnaliseService.addFinalAnalise(flightOrder, Cabin.BUSINESS);
                logger.info("Final analise for order {} is completed (BUSINESS CLASS)", flightOrder.getId());
            } catch (GotNullException ex){
                ex.printStackTrace();
            }

            try {
                scheduledAnaliseService.addFinalAnalise(flightOrder, Cabin.FIRST_CLASS);
                logger.info("Final analise for order {} is completed (FIRST CLASS)", flightOrder.getId());
            } catch (GotNullException ex){
                ex.printStackTrace();
            }

        }
    }
}
