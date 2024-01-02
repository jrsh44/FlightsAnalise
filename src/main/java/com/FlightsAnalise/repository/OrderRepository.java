package com.FlightsAnalise.repository;

import com.FlightsAnalise.model.FlightOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<FlightOrder, Integer> {
}
