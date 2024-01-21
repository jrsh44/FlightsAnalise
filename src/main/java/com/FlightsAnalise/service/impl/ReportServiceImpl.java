package com.FlightsAnalise.service.impl;

import com.FlightsAnalise.exceptions.ResourceNotFoundException;
import com.FlightsAnalise.model.FinalAnalise;
import com.FlightsAnalise.model.FlightOrder;
import com.FlightsAnalise.model.Report;
import com.FlightsAnalise.model.SingleAnalise;
import com.FlightsAnalise.repository.ReportRepository;
import com.FlightsAnalise.service.AnaliseService;
import com.FlightsAnalise.service.OrderService;
import com.FlightsAnalise.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    AnaliseService analiseService;

    @Autowired
    OrderService orderService;

    @Override
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @Override
    public Report getReportById(int id) {
        //Throws exception if there is no order for this id
        orderService.getById(id);

        return reportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Report for ID = " + id + " isn't done yet."));
    }

    @Override
    public void delReportById(int id) {
        Report report = getReportById(id);
        FlightOrder flightOrder = report.getFlightOrder();

        //Deleting report
        reportRepository.delete(report);

        //Deleting all final analyses done for flightOrder
        analiseService.getAllFinalAnalise().stream()
                .filter(s -> s.getFlightOrder().getId() == flightOrder.getId())
                .mapToInt(FinalAnalise::getId)
                .forEach(i -> analiseService.deleteFinalAnaliseById(i));

        //Deleting all single analyses (and saved flights) done for flightOrder
        analiseService.getAllSingleAnalise().stream()
                .filter(s -> s .getFlightOrder().getId() == flightOrder.getId())
                .mapToInt(SingleAnalise::getId)
                .forEach(i -> analiseService.deleteSingleAnaliseById(i));

        //Deleting order
        orderService.delById(id);
    }

    @Override
    public void delAll() {
        reportRepository.deleteAll();
    }
}
