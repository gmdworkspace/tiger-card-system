package com.tigercard.fareengine.config;

import com.tigercard.fareengine.domain.Zone;

import java.util.HashMap;
import java.util.Map;

public class WeeklyFareCapConfig extends FareCapConfig {

    private static final Map<Zone, Map<Zone, Double>> weeklyFareCap = new HashMap<>();

    public WeeklyFareCapConfig() {
        initWeeklyCapFare();
    }

    @Override
    public Double fetchFareCap(Zone source, Zone destination) {
        if (source == null || destination == null)
            return null;
        return weeklyFareCap.get(source).get(destination);
    }

    private static void initWeeklyCapFare() {
        weeklyFareCap.put(Zone.ONE, new HashMap<>() {
            {
                put(Zone.ONE, 500.00);
                put(Zone.TWO, 600.00);
            }
        });
        weeklyFareCap.put(Zone.TWO, new HashMap<>() {
            {
                put(Zone.ONE, 600.00);
                put(Zone.TWO, 400.00);
            }
        });
    }
}
