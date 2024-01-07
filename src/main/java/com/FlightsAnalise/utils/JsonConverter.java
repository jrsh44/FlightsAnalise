package com.FlightsAnalise.utils;

import com.FlightsAnalise.model.receivedJson.Flight;
import com.FlightsAnalise.model.receivedJson.KiwiData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {
    public static KiwiData convertJsonToKiwiData(JsonNode jsonNode){
        KiwiData kiwiData = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            kiwiData = objectMapper.readValue(jsonNode.toString(), KiwiData.class);
            System.out.println("Search Information:");
            System.out.println("Currency: " + kiwiData.getCurrency());
            System.out.println("Rate to EUR: " + kiwiData.getFxRate());
            System.out.println("Search ID: " + kiwiData.getSearchId());
            System.out.println();

            System.out.println("Flight Data:");
            for(Flight flight: kiwiData.getData()){
                System.out.println("From: " + flight.getCityFrom());
                System.out.println("To: " + flight.getCityTo());
                System.out.println("When: " + flight.getUtcDeparture());
                System.out.println("For how many nights: " + flight.getNightsInDest());
                System.out.println("Price: " + flight.getPrice() + " " +  kiwiData.getCurrency());
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return kiwiData;
    }
}
