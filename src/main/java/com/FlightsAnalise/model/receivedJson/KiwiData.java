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
public class KiwiData {
    @JsonProperty("search_id")
    protected String searchId;
    protected String currency;
    @JsonProperty("fx_rate")
    protected double fxRate;
    protected ArrayList<Flight> data;
    protected int _results;
}
