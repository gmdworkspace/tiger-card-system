package com.tigercard.fareengine.service;

import com.tigercard.fareengine.api.TripFareProvider;
import com.tigercard.fareengine.config.TripFareConfig;
import com.tigercard.fareengine.domain.HourType;
import com.tigercard.fareengine.domain.Zone;
import com.tigercard.fareengine.exception.TripFareException;

public class ZonedHourTripFareService implements TripFareProvider {

    private TripFareConfig tripFareConfig;

    public ZonedHourTripFareService() {
        tripFareConfig = new TripFareConfig();
    }

    @Override
    public Double getFareForTheTrip(Zone source, Zone destination, HourType hourType) throws TripFareException {
        if (source == null || destination == null || hourType == null) {
            throw new TripFareException("source, destination and hour type params are required");
        }
        return tripFareConfig.findTripFareForHourType(source, destination, hourType);
    }
}
