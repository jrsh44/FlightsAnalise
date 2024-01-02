package com.FlightsAnalise.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class FinalAnalise {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private FlightOrder flightOrder;

    @Column(name = "cabin")
    private String cabin;

    @Column(name = "start_price")
    private double startPrice;

    @Column(name = "end_price")
    private double endPrice;

    @Column(name = "average_price")
    private double averagePrice;

    @Column(name = "price_change")
    private double priceChange;

//    @Column(name = "average_duration")
//    private double averageDuration;

    @Column(name = "start_amount")
    private double startAmount;

    @Column(name = "end_amount")
    private double endAmount;

    @Column(name = "amount_change")
    private int amountChange;

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
