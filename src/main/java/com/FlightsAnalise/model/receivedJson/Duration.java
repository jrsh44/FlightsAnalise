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
public class Duration{
    protected int departure;
    @JsonProperty("return")
    protected int myReturn;
    protected int total;
}
