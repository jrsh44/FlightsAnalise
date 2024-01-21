package com.FlightsAnalise.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class SingleAnalise {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private FlightOrder flightOrder;

    @Column(name = "average_price")
    private double averagePrice;

    @Column(name = "median_nights_in_dest")
    private int medianNightsInDest;

    @Column(name = "cabin")
    private String cabin;

    @Column(name = "amount")
    private int amount;

    @OneToOne
    @JoinColumn(name = "best_offer", referencedColumnName = "id")
    private SingleFlight bestOffer;

    @OneToOne
    @JoinColumn(name = "worst_offer", referencedColumnName = "id")
    private SingleFlight worstOffer;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

}
