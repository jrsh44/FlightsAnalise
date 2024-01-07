package com.FlightsAnalise.model;

import com.FlightsAnalise.exceptions.BadBuilderException;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class KiwiOrderBuilder {
    private String flyFrom;
    private String flyTo;
    private String dateFrom;
    private String dateTo;
    private int adults = 1;
    private int children = 0;
    private Currency curr = Currency.EUR;
    private int maxStopovers = 0;
    private int nightsInDestFrom = 1;
    private int nightsInDestTo = 10;
    private int limit = 20;

    public KiwiOrderBuilder(String flyFrom, String flyTo, String dateFrom, String dateTo){
        this.flyFrom = flyFrom;
        this.flyTo = flyTo;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
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

}
