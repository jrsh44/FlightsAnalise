package com.FlightsAnalise.model;

public enum Currency {
    GBP("GPB"),
    USD("USD"),
    PLN("PLN"),
    EUR("EUR");

    public final String label;

    private Currency(String label) {
        this.label = label;
    }

}
