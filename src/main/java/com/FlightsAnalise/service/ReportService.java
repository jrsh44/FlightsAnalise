package com.FlightsAnalise.service;

import com.FlightsAnalise.model.Report;

import java.util.List;

public interface ReportService {
    List<Report> getAllReports();

    Report getReportById(int id);

    void delReportById(int id);

    void delAll();
}
