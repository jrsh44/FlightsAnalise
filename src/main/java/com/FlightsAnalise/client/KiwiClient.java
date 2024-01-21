package com.FlightsAnalise.client;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name="KiwiClient",
        url="https://api.tequila.kiwi.com/v2")
public interface KiwiClient {

    @GetMapping("/search")
    JsonNode search(@RequestHeader("apikey") String apikey,
                    @RequestParam("fly_from") String flyFrom,
                    @RequestParam("fly_to") String flyTo,
                    @RequestParam("date_from") String dateFrom,
                    @RequestParam("date_to") String dateTo,
                    @RequestParam("adults") int adults,
                    @RequestParam("children") int children,
                    @RequestParam("curr") String curr,
                    @RequestParam("max_stopovers") int maxStopovers,
                    @RequestParam("nights_in_dst_from") int maxInDestFrom,
                    @RequestParam("nights_in_dst_to") int maxInDestTo,
                    @RequestParam("limit") int limit);

    @GetMapping("/search")
    JsonNode search(@RequestHeader("apikey") String apikey,
                    @RequestParam("fly_from") String flyFrom,
                    @RequestParam("fly_to") String flyTo,
                    @RequestParam("date_from") String dateFrom,
                    @RequestParam("date_to") String dateTo,
                    @RequestParam("adults") int adults,
                    @RequestParam("children") int children,
                    @RequestParam("curr") String curr,
                    @RequestParam("max_stopovers") int maxStopovers,
                    @RequestParam("nights_in_dst_from") int maxInDestFrom,
                    @RequestParam("nights_in_dst_to") int maxInDestTo,
                    @RequestParam("limit") int limit,
                    @RequestParam("selected_cabins") String cabin);
}
