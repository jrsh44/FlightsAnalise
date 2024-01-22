package com.FlightsAnalise.utils;

import com.FlightsAnalise.model.receivedJson.KiwiData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {
    public static KiwiData convertJsonToKiwiData(JsonNode jsonNode) {
        KiwiData kiwiData = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            kiwiData = objectMapper.readValue(jsonNode.toString(), KiwiData.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kiwiData;
    }
}
