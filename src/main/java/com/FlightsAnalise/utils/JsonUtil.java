package com.FlightsAnalise.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static JsonNode convertSnakeCaseToCamelCase(JsonNode input) {
        if (input.isObject()) {
            ObjectNode result = objectMapper.createObjectNode();
            input.fields().forEachRemaining(entry -> result.set(convertSnakeCaseToCamelCase(entry.getKey()), convertSnakeCaseToCamelCase(entry.getValue())));
            return result;
        } else if (input.isArray()) {
            ArrayNode result = objectMapper.createArrayNode();
            input.elements().forEachRemaining(element -> result.add(convertSnakeCaseToCamelCase(element)));
            return result;
        } else {
            return input;
        }
    }

    private static String convertSnakeCaseToCamelCase(String input) {
        String[] parts = input.split("_");
        StringBuilder camelCase = new StringBuilder(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            camelCase.append(Character.toUpperCase(parts[i].charAt(0))).append(parts[i].substring(1));
        }
        return camelCase.toString();
    }

}
