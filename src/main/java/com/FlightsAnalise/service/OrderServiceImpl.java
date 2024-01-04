package com.FlightsAnalise.service;

import com.FlightsAnalise.client.KiwiClient;
import com.FlightsAnalise.exceptions.BadRequestException;
import com.FlightsAnalise.exceptions.ResourceNotFoundException;
import com.FlightsAnalise.exceptions.UnprocessableEntityException;
import com.FlightsAnalise.model.FlightOrder;
import com.FlightsAnalise.repository.OrderRepository;
import com.FlightsAnalise.utils.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {

    @Value("${apikey}")
    private String apikey;

    @Autowired
    private KiwiClient kiwiClient;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public FlightOrder add(FlightOrder flightOrder) {
        checkVariables(flightOrder);
        checkDates(flightOrder.getDateFrom(), flightOrder.getDateTo());
        JsonNode data;
        try{
            data = kiwiClient.search(
                    apikey,
                    flightOrder.getFlyFrom(),
                    flightOrder.getFlyTo(),
                    flightOrder.getDateFrom(),
                    flightOrder.getDateTo());
        } catch (FeignException.UnprocessableEntity ex) {
            throw new UnprocessableEntityException("Couldn't find given location");
        } catch (Exception ex) {
            throw new BadRequestException("Unable to validate order");
        }
        System.out.println(JsonUtil.convertSnakeCaseToCamelCase(data));
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


    public void checkDates(String firstDate, String secondDate) {
        LocalDate fDate, sDate;
        DateTimeFormatter formatter;
        try {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            fDate = LocalDate.parse(firstDate, formatter);
            sDate = LocalDate.parse(secondDate, formatter);
        } catch (Exception ex) {
            throw new BadRequestException("Date must be in the form of dd/mm/rrrr");
        }
        if (!(fDate.isAfter(LocalDate.now().minusDays(1)) && sDate.isAfter(LocalDate.now().minusDays(1)))) {
            throw new BadRequestException("Date mustn't be earlier than: " + LocalDate.now().format(formatter));
        }
        if (fDate.isAfter(sDate)) {
            throw new BadRequestException("Variable 'dateTo' mustn't contain date earlier than 'dateFrom");
        }
    }

    public void checkVariables(FlightOrder flightOrder){
        if (Objects.isNull(flightOrder.getFlyTo()) || Objects.isNull(flightOrder.getFlyFrom()) || Objects.isNull(flightOrder.getDateTo()) || Objects.isNull(flightOrder.getDateFrom())) {
            throw new BadRequestException("Required parameters: flyFrom, flyTo, dateFrom, dateTo.");
        }
    }
}
