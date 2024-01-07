package com.FlightsAnalise.service;

import com.FlightsAnalise.model.KiwiOrderBuilder;
import com.FlightsAnalise.model.receivedJson.KiwiData;

public interface KiwiService {
    KiwiData search(KiwiOrderBuilder kiwiOrder);
}
