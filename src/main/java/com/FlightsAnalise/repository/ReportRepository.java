package com.FlightsAnalise.repository;

import com.FlightsAnalise.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Integer> {
}
