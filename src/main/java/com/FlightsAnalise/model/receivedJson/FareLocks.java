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
    protected ArrayList<Object> eUR;
    @JsonProperty("USD")
    protected ArrayList<Object> uSD;
    @JsonProperty("GBP")
    protected ArrayList<Object> gBP;
    @JsonProperty("PLN")
    protected ArrayList<Object> pLN;
}
