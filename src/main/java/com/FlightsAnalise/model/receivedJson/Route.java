package com.FlightsAnalise.model.receivedJson;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Route{
    protected String id;
    @JsonProperty("combination_id")
    protected String combinationId;
    protected String flyFrom;
    protected String flyTo;
    protected String cityFrom;
    protected String cityCodeFrom;
    protected String cityTo;
    protected String cityCodeTo;
    @JsonProperty("local_departure")
    protected Date localDeparture;
    @JsonProperty("utc_departure")
    protected Date utcDeparture;
    @JsonProperty("local_arrival")
    protected Date localArrival;
    @JsonProperty("utc_arrival")
    protected Date utcArrival;
    protected String airline;
    @JsonProperty("flight_no")
    protected int flightNo;
    @JsonProperty("operating_carrier")
    protected String operatingCarrier;
    @JsonProperty("operating_flight_no")
    protected String operatingFlightNo;
    @JsonProperty("fare_basis")
    protected String fareBasis;
    @JsonProperty("fare_category")
    protected String fareCategory;
    @JsonProperty("fare_classes")
    protected String fareClasses;
    @JsonProperty("return")
    protected int myReturn;
    @JsonProperty("bags_recheck_required")
    protected boolean bagsRecheckRequired;
    @JsonProperty("vi_connection")
    protected boolean viConnection;
    protected boolean guarantee;
    protected Object equipment;
    @JsonProperty("vehicle_type")
    protected String vehicleType;
}
