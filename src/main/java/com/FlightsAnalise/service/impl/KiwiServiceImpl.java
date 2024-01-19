package com.FlightsAnalise.service.impl;

import com.FlightsAnalise.client.KiwiClient;
import com.FlightsAnalise.exceptions.BadBuilderException;
import com.FlightsAnalise.exceptions.UnprocessableEntityException;
import com.FlightsAnalise.model.KiwiOrderBuilder;
import com.FlightsAnalise.model.receivedJson.KiwiData;
import com.FlightsAnalise.service.KiwiService;
import com.FlightsAnalise.utils.JsonConverter;
import com.fasterxml.jackson.databind.JsonNode;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KiwiServiceImpl implements KiwiService {
    @Value("${apikey}")
    private String apikey;

    @Autowired
    private KiwiClient kiwiClient;

    @Override
    public KiwiData search(KiwiOrderBuilder kiwiOrder){
        JsonNode jsonNode;

        try{
            if (kiwiOrder.getCabin() == null) {
                jsonNode = kiwiClient.search(
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
            } else {
                jsonNode = kiwiClient.search(
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
                        kiwiOrder.getLimit(),
                        kiwiOrder.getCabin().label
                );
            }
        } catch (FeignException.UnprocessableEntity ex) {
            throw new UnprocessableEntityException("Couldn't find given location");
        }
        catch (Exception ex) {
            throw new BadBuilderException("Unable to validate order");
        }

        return JsonConverter.convertJsonToKiwiData(jsonNode);
    }
}
