package com.FlightsAnalise.service.scheduler;

import com.FlightsAnalise.model.Cabin;
import com.FlightsAnalise.model.FlightOrder;
import com.FlightsAnalise.service.ScheduledAnaliseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ScheduledAnalise {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledAnalise.class);

    private final FlightOrder flightOrder;
    private final ScheduledAnaliseService scheduledAnaliseService;
    private final AtomicInteger executionCount = new AtomicInteger(0);
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    public ScheduledAnalise(FlightOrder flightOrder, ScheduledAnaliseService scheduledAnaliseService) {
        this.flightOrder = flightOrder;
        this.scheduledAnaliseService = scheduledAnaliseService;
        executorService.scheduleAtFixedRate(this::scheduledTask, 0, this.flightOrder.getTestTimeGap(), TimeUnit.MINUTES);
    }

    private void scheduledTask() {
        int testNumber = executionCount.incrementAndGet();

        for (Cabin cabin : Cabin.values()) {
            System.out.println(cabin);
            scheduledAnaliseService.addSingleAnalise(flightOrder, cabin);
            logger.info("Analise nr {} for order {} is completed ({})", testNumber, flightOrder.getId(), cabin);
        }

        if (testNumber == flightOrder.getNumOfTests()) {
            for (Cabin cabin : Cabin.values()) {
                scheduledAnaliseService.addFinalAnalise(flightOrder, cabin);
                logger.info("Final analise for order {} is completed ({})", flightOrder.getId(), cabin);
            }
            scheduledAnaliseService.addReport(flightOrder);
            logger.info("Report for order {} is completed", flightOrder.getId());
            executorService.shutdown();
        }

    }
}
