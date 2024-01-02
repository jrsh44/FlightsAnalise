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

    @Column(name = "flyFrom", nullable = false)
    private String flyFrom;

    @Column(name = "flyTo", nullable = false)
    private String flyTo;

    @Column(name = "dateFrom", nullable = false)
    private String dateFrom;

    @Column(name = "dateTo", nullable = false)
    private String dateTo;

    @Column(name = "adults")
    private int adults = 1;

    @Column(name = "children")
    private int children = 0;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

}
