package com.FlightsAnalise.repository;

import com.FlightsAnalise.model.SingleFlight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<SingleFlight, Integer> {
}
