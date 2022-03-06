package com.tigercard.fareengine.config;

import com.tigercard.fareengine.domain.Zone;

import java.util.HashMap;
import java.util.Map;

public class DailyFareCapConfig extends FareCapConfig {

    private static final Map<Zone, Map<Zone, Double>> dailyFareCap = new HashMap<>();

    public DailyFareCapConfig() {
        initDailyCapFare();
    }

    @Override
    public Double fetchFareCap(Zone source, Zone destination) {
        if (source == null || destination == null)
            return null;
        return dailyFareCap.get(source).get(destination);
    }

    private static void initDailyCapFare() {
        dailyFareCap.put(Zone.ONE, new HashMap<>() {
            {
                put(Zone.ONE, 100.00);
                put(Zone.TWO, 120.00);
            }
        });
        dailyFareCap.put(Zone.TWO, new HashMap<>() {
            {
                put(Zone.ONE, 120.00);
                put(Zone.TWO, 80.00);
            }
        });
    }
}
