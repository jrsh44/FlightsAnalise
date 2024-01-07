package com.FlightsAnalise.service;

import com.FlightsAnalise.client.KiwiClient;
import com.FlightsAnalise.exceptions.BadBuilderException;
import com.FlightsAnalise.exceptions.ResourceNotFoundException;
import com.FlightsAnalise.exceptions.UnprocessableEntityException;
import com.FlightsAnalise.model.FlightOrder;
import com.FlightsAnalise.model.KiwiOrderBuilder;
import com.FlightsAnalise.model.receivedJson.KiwiData;
import com.FlightsAnalise.repository.OrderRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Value("${apikey}")
    private String apikey;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private KiwiService kiwiService;

    @Override
    public FlightOrder add(FlightOrder flightOrder) {

        //Checks if there are any possible flights
        KiwiData data = kiwiService.search(new KiwiOrderBuilder(
                flightOrder.getFlyFrom(),
                flightOrder.getFlyTo(),
                flightOrder.getDateFrom(),
                flightOrder.getDateTo())
                .adults(flightOrder.getAdults())
                .children(flightOrder.getChildren()));
        if(data.getData().isEmpty()){
            throw new BadBuilderException("There aren't any direct flights between these locations!");
        }
        return orderRepository.save(flightOrder);
    }

    @Override
    public List<FlightOrder> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public FlightOrder getById(int id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Couldn't find order with ID = " + id));
    }

    @Override
    public void delById(int id) {
        orderRepository.delete(getById(id));
    }
}
