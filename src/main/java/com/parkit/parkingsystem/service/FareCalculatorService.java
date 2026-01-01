package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket, boolean discount){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        long inHour = ticket.getInTime().getTime();
        long outHour = ticket.getOutTime().getTime();

        double duration = (double) (outHour - inHour) / (60 * 60 * 1000);

        //If duration < 30 minutes
        if(duration < 0.5){
            ticket.setPrice(0);
            return;
        }

        switch (ticket.getParkingSpot().getParkingType()) {
                    case CAR: {
                        if(discount)
                            ticket.setPrice(0.95 * (duration * Fare.CAR_RATE_PER_HOUR));
                        else
                            ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
                        break;
                    }
                    case BIKE: {
                        if(discount)
                            ticket.setPrice(0.95 * (duration * Fare.BIKE_RATE_PER_HOUR));
                        else
                            ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
                        break;
                    }
                    default:
                        throw new IllegalArgumentException("Unkown Parking Type");
        }

    }

    public void calculateFare(Ticket ticket){

        this.calculateFare(ticket, true);
    }
}