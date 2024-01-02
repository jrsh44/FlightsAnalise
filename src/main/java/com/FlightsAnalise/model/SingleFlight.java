package com.FlightsAnalise.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class SingleFlight {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "utc_departure")
    private String utcDeparture;

    @Column(name = "utc_arrival")
    private String utcArrival;

    @Column(name = "nights_in_dest")
    private int nightsInDest;

    @Column(name = "price")
    private double price;

    @Column(name = "duration")
    private int duration;
}
