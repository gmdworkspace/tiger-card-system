package com.tigercard.fareengine.service;

import com.tigercard.fareengine.api.FareCapProvider;
import com.tigercard.fareengine.config.DailyFareCapConfig;
import com.tigercard.fareengine.config.FareCapConfig;
import com.tigercard.fareengine.domain.Shuttle;
import com.tigercard.fareengine.exception.FareCapException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.tigercard.fareengine.helper.FareCapHelper.*;

@Slf4j
public class DailyFareCapService implements FareCapProvider {

    private FareCapConfig fareCapConfig;

    public DailyFareCapService() {
        fareCapConfig = new DailyFareCapConfig();
    }

    @Override
    public Double getTotalFare(List<Shuttle> shuttles) throws FareCapException {
        Double totalFare = RATE_ZERO_AMOUNT;
        if (shuttles == null || shuttles.isEmpty())
            throw new FareCapException("Shuttles should not be null or empty");
        if (!isAllShuttlesOnSameDay(shuttles))
            throw new FareCapException("All the shuttles should be on the same day");

        Double farthestFareCap = getFarthestCap(shuttles, fareCapConfig);
        for (Shuttle shuttle : shuttles) {
            log.info("Travel from " + shuttle.getSource() + " to " + shuttle.getDestination() + " on time:" + shuttle.getDateTimeOfTravel());
            Double actualCapedFare = getActualCapedFare(totalFare, shuttle.getFare(), farthestFareCap);
            log.info("Actual Trip Fare:" + actualCapedFare);
            totalFare += actualCapedFare;
        }
        return totalFare;
    }

    private Boolean isAllShuttlesOnSameDay(List<Shuttle> shuttles) {
        return shuttles.stream().map(s -> s.getDateTimeOfTravel().toLocalDate()).distinct().count() == 1;
    }
}
