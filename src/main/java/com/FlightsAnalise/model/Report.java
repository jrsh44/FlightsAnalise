package com.FlightsAnalise.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Report {

    @Id
    private int id;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private FlightOrder flightOrder;

    @Column(name = "start_price")
    private double startPrice;

    @Column(name = "end_price")
    private double endPrice;

    @Column(name = "average_price")
    private double averagePrice;

    @Column(name = "price_change")
    private double priceChange;

    @Column(name = "start_amount")
    private int startAmount;

    @Column(name = "end_amount")
    private int endAmount;

    @Column(name = "amount_change")
    private int amountChange;

    @OneToOne
    @JoinColumn(name = "economy_analise", referencedColumnName = "id")
    private FinalAnalise economyAnalise;

    @OneToOne
    @JoinColumn(name = "economy_premium_analise", referencedColumnName = "id")
    private FinalAnalise economyPremiumAnalise;

    @OneToOne
    @JoinColumn(name = "business_analise", referencedColumnName = "id")
    private FinalAnalise businessAnalise;

    @OneToOne
    @JoinColumn(name = "first_class_analise", referencedColumnName = "id")
    private FinalAnalise firstClassAnalise;


    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

}
