package com.tigercard.fareengine.api;

import com.tigercard.fareengine.domain.HourType;
import com.tigercard.fareengine.domain.Zone;
import com.tigercard.fareengine.exception.TripFareException;

public interface TripFareProvider {
    /**
     * Get the fixed price for the trip
     * @param source
     * @param destination
     * @param hourType
     * @return Uncapped fare for the travel - Double
     * @throws TripFareException
     */
    Double getFareForTheTrip(Zone source, Zone destination, HourType hourType) throws TripFareException;
}
