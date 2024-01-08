package com.FlightsAnalise.model.receivedJson;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Conversion{
    @JsonProperty("EUR")
    protected double eur;
    @JsonProperty("USD")
    protected int usd;
    @JsonProperty("GBP")
    protected int gbp;
    @JsonProperty("PLN")
    protected int pln;
}