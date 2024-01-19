package com.FlightsAnalise.model;

public enum Cabin {
    ECONOMY("M"),
    PREMIUM_ECONOMY("W"),
    BUSINESS("C"),
    FIRST_CLASS("F");

    public final String label;

    private Cabin(String label) {
        this.label = label;
    }

}
