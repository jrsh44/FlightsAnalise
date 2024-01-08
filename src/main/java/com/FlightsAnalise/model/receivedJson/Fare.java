package com.FlightsAnalise.model.receivedJson;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Fare{
    protected double adults;
    protected double children;
    protected double infants;
}
