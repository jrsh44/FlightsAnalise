package com.FlightsAnalise.model.receivedJson;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class FareLocks{
    @JsonProperty("EUR")
    protected ArrayList<Object> eur;
    @JsonProperty("USD")
    protected ArrayList<Object> usd;
    @JsonProperty("GBP")
    protected ArrayList<Object> gbp;
    @JsonProperty("PLN")
    protected ArrayList<Object> pln;
}
