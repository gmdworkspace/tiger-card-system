package com.tigercard.fareengine.config;

import com.tigercard.fareengine.domain.HourType;
import com.tigercard.fareengine.domain.Zone;

import java.util.HashMap;
import java.util.Map;

public class TripFareConfig {

    private static final Map<Zone, Map<Zone, Double>> peakZoneFareMap = new HashMap<>();
    private static final Map<Zone, Map<Zone, Double>> offPeakZoneFareMap = new HashMap<>();
    private static final Map<HourType, Map<Zone, Map<Zone, Double>>> fareMap = new HashMap<>();

    public TripFareConfig() {
        initPeakHourFare();
        initOffPeakHourFare();
        initFareMap();
    }

    public Double findTripFareForHourType(Zone source, Zone destination, HourType hourType) {
        if(source == null || destination == null || hourType == null)
            return null;
        return fareMap.get(hourType).get(source).get(destination);
    }

    private static void initPeakHourFare() {
        peakZoneFareMap.put(Zone.ONE, new HashMap<>() {
            {
                put(Zone.ONE, 30.00);
                put(Zone.TWO, 35.00);
            }
        });
        peakZoneFareMap.put(Zone.TWO, new HashMap<>() {
            {
                put(Zone.ONE, 35.00);
                put(Zone.TWO, 25.00);
            }
        });
    }

    private static void initOffPeakHourFare() {
        offPeakZoneFareMap.put(Zone.ONE, new HashMap<>() {
            {
                put(Zone.ONE, 25.00);
                put(Zone.TWO, 30.00);
            }
        });
        offPeakZoneFareMap.put(Zone.TWO, new HashMap<>() {
            {
                put(Zone.ONE, 30.00);
                put(Zone.TWO, 20.00);
            }
        });
    }

    private static void initFareMap() {
        fareMap.put(HourType.PEAK_HOUR, peakZoneFareMap);
        fareMap.put(HourType.OFF_PEAK_HOUR, offPeakZoneFareMap);
    }
}
