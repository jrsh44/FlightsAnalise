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
    protected double eUR;
    @JsonProperty("USD")
    protected int uSD;
    @JsonProperty("GBP")
    protected int gBP;
    @JsonProperty("PLN")
    protected int pLN;
}