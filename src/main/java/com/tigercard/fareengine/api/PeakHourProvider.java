package com.tigercard.fareengine.api;

import com.tigercard.fareengine.domain.HourType;
import com.tigercard.fareengine.exception.PeakHourException;

import java.time.DayOfWeek;
import java.time.LocalTime;

public interface PeakHourProvider {
    /**
     * Get the hour type based on DayOfWeek and timeOfTravel
     *
     * @param dayOfWeek
     * @param timeOfTravel
     * @return HourType - PEAK or OFF_PEAK hour
     * @throws PeakHourException
     */
    HourType getHourType(DayOfWeek dayOfWeek, LocalTime timeOfTravel) throws PeakHourException;
}
