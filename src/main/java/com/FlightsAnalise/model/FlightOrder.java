package com.FlightsAnalise.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class FlightOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "fly_from", nullable = false)
    private String flyFrom;

    @Column(name = "fly_to", nullable = false)
    private String flyTo;

    @Column(name = "date_from", nullable = false)
    private String dateFrom;

    @Column(name = "date_to", nullable = false)
    private String dateTo;

    @Column(name = "adults")
    private int adults = 1;

    @Column(name = "children")
    private int children = 0;

    @Column(name = "currency")
    private Currency currency = Currency.EUR;

    @Column(name = "num_of_tests")
    private int numOfTests = 0;

    @Column(name = "test_time_gap")
    private int testTimeGap = 0;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

}
