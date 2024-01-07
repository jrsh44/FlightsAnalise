package com.FlightsAnalise.model.receivedJson;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Flight{
    protected String id;
    protected String flyFrom;
    protected String flyTo;
    protected String cityFrom;
    protected String cityCodeFrom;
    protected String cityTo;
    protected String cityCodeTo;
    protected CountryFrom countryFrom;
    protected CountryTo countryTo;
    @JsonProperty("local_departure")
    protected Date localDeparture;
    @JsonProperty("utc_departure")
    protected Date utcDeparture;
    @JsonProperty("local_arrival")
    protected Date localArrival;
    @JsonProperty("utc_arrival")
    protected Date utcArrival;
    protected int nightsInDest;
    protected double quality;
    protected double distance;
    protected Duration duration;
    protected int price;
    protected Conversion conversion;
    protected Fare fare;
    @JsonProperty("discount_data")
    protected Object discountData;
    @JsonProperty("fare_locks")
    protected FareLocks fareLocks;
    @JsonProperty("bags_price")
    protected BagsPrice bagsPrice;
    protected Baglimit baglimit;
    protected Availability availability;
    protected ArrayList<String> airlines;
    protected ArrayList<Route> route;
    @JsonProperty("booking_token")
    protected String bookingToken;
    @JsonProperty("deep_link")
    protected String deepLink;
    @JsonProperty("facilitated_booking_available")
    protected boolean facilitatedBookingAvailable;
    @JsonProperty("pnr_count")
    protected int pnrCount;
    @JsonProperty("has_airport_change")
    protected boolean hasAirportChange;
    @JsonProperty("technical_stops")
    protected int technicalStops;
    @JsonProperty("throw_away_ticketing")
    protected boolean throwAwayTicketing;
    @JsonProperty("hidden_city_ticketing")
    protected boolean hiddenCityTicketing;
    @JsonProperty("virtual_interlining")
    protected boolean virtualInterlining;
}
