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
public class BagsPrice{
    @JsonProperty("1")
    protected double bag1Price;
    @JsonProperty("2")
    protected  double bag2Price;
}
