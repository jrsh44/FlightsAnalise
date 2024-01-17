package com.FlightsAnalise.service.scheduler;

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
            scheduledAnaliseService.addSingleAnalise(flightOrder);
            logger.info("Analise nr {} for order {} is completed", testsCounter, flightOrder.getId());
        }
        if (testsCounter == flightOrder.getNumOfTests()) {
            scheduledAnaliseService.addFinalAnalise(flightOrder);
            logger.info("Final analise for order {} is completed", flightOrder.getId());
        }
    }
}
