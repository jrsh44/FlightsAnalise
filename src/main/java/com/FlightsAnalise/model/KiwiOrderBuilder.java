package com.FlightsAnalise.model;

import com.FlightsAnalise.exceptions.BadBuilderException;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Getter
@Setter
public class KiwiOrderBuilder {
    private String flyFrom;
    private String flyTo;
    private String dateFrom;
    private String dateTo;
    private int numOfTests;
    private int testTimeGap;
    private int adults = 1;
    private int children = 0;
    private Currency curr = Currency.EUR;
    private int maxStopovers = 0;
    private int nightsInDestFrom = 1;
    private int nightsInDestTo = 10;
    private int limit = 20;

    public KiwiOrderBuilder(String flyFrom, String flyTo, String dateFrom, String dateTo, int numOfTests, int testTimeGap){
        this.flyFrom = flyFrom;
        this.flyTo = flyTo;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        if(testTimeGap <= 0){
            throw new BadBuilderException("There must be at least 1 minute gap between tests!");
        }
        this.testTimeGap = testTimeGap;
        if(numOfTests <= 0){
            throw new BadBuilderException("There must be at least 1 test!");
        }
        this.numOfTests = numOfTests;
        checkVariables();
        checkDates(dateFrom, dateTo);
    }
    public KiwiOrderBuilder adults(int adults){
        if(adults <= 0){
            throw new BadBuilderException("There must be at least 1 adult to travel!");
        }
        this.adults = adults;
        return this;
    }

    public KiwiOrderBuilder children(int children){
        if(children < 0) {
            throw new BadBuilderException("There cannot be negative children!");
        }
        this.children = children;
        return this;
    }

    public KiwiOrderBuilder curr(Currency curr){
        this.curr = curr;
        return this;
    }

    public KiwiOrderBuilder maxStopovers(int maxStopovers){
        if (maxStopovers < 0){
            throw new BadBuilderException("There cannot be negative stopovers!");
        }
        this.maxStopovers = maxStopovers;
        return this;
    }

    public KiwiOrderBuilder nightsInDestFrom(int value){
        if (value < 0){
            throw new BadBuilderException("Nights range cannot start from negative value!");
        }
        this.nightsInDestFrom = value;
        return this;
    }

    public KiwiOrderBuilder nightsInDestTo(int value){
        if(value < nightsInDestFrom){
            throw new BadBuilderException("Bad nights range!");
        }
        this.nightsInDestTo = value;
        return this;
    }

    private void checkDates(String firstDate, String secondDate) {
        LocalDate fDate, sDate;
        DateTimeFormatter formatter;
        try {
            formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            fDate = LocalDate.parse(firstDate, formatter);
            sDate = LocalDate.parse(secondDate, formatter);
        } catch (Exception ex) {
            throw new BadBuilderException("Date must be in the form of dd/mm/rrrr");
        }
        if (!(fDate.isAfter(LocalDate.now().minusDays(1)) && sDate.isAfter(LocalDate.now().minusDays(1)))) {
            throw new BadBuilderException("Date mustn't be earlier than: " + LocalDate.now().format(formatter));
        }
        if (fDate.isAfter(sDate)) {
            throw new BadBuilderException("Variable 'dateTo' mustn't contain date earlier than 'dateFrom");
        }
    }
    private void checkVariables(){
        if (Objects.isNull(this.dateFrom) || Objects.isNull(this.dateTo) || Objects.isNull(this.flyFrom) || Objects.isNull(this.flyTo) || Objects.isNull(this.numOfTests) || Objects.isNull(this.testTimeGap)) {
            throw new BadBuilderException("Required parameters: flyFrom, flyTo, dateFrom, dateTo, numOfTests, testTimeGap.");
        }
    }

}
