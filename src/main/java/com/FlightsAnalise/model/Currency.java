package com.FlightsAnalise.model;

public enum Currency {
    GBP("GPB"),
    USD("USD"),
    PLN("PLN"),
    EUR("EUR");

    public final String label;

    Currency(String label) {
        this.label = label;
    }

}
