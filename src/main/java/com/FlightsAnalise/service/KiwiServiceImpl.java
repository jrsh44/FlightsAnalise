package com.FlightsAnalise.service;

import com.FlightsAnalise.client.KiwiClient;
import com.FlightsAnalise.model.KiwiOrderBuilder;
import com.FlightsAnalise.model.receivedJson.KiwiData;
import com.FlightsAnalise.utils.JsonConverter;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KiwiServiceImpl implements KiwiService{
    @Value("${apikey}")
    private String apikey;

    @Autowired
    private KiwiClient kiwiClient;


    @Override
    public KiwiData search(KiwiOrderBuilder kiwiOrder){
        JsonNode jsonNode =  kiwiClient.search(
                apikey,
                kiwiOrder.getFlyFrom(),
                kiwiOrder.getFlyTo(),
                kiwiOrder.getDateFrom(),
                kiwiOrder.getDateTo(),
                kiwiOrder.getAdults(),
                kiwiOrder.getChildren(),
                kiwiOrder.getCurr().toString(),
                kiwiOrder.getMaxStopovers(),
                kiwiOrder.getNightsInDestFrom(),
                kiwiOrder.getNightsInDestTo(),
                kiwiOrder.getLimit()
        );
        return JsonConverter.convertJsonToKiwiData(jsonNode);
    }
}
