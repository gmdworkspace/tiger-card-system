package com.tigercard.fareengine.helper;

import com.tigercard.fareengine.config.FareCapConfig;
import com.tigercard.fareengine.domain.Shuttle;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class FareCapHelper {
    public static final Double RATE_ZERO_AMOUNT = 0.00;

    public static Double getFarthestCap(List<Shuttle> shuttles, FareCapConfig fareCapConfig) {
        if (shuttles == null || fareCapConfig == null)
            return null;
        Double farthestFareCap = RATE_ZERO_AMOUNT;
        for (Shuttle shuttle : shuttles) {
            Double fareCap = fareCapConfig.fetchFareCap(shuttle.getSource(), shuttle.getDestination());
            if (farthestFareCap < fareCap) {
                farthestFareCap = fareCap;
            }
        }
        return farthestFareCap;
    }

    public static Double getActualCapedFare(Double totalFare, Double travelFare, Double farthestFareCap) {
        if(totalFare.equals(farthestFareCap)) {
            return RATE_ZERO_AMOUNT;
        }
        if(totalFare + travelFare > farthestFareCap) {
            return farthestFareCap - totalFare;
        }
        return travelFare;
    }
}
