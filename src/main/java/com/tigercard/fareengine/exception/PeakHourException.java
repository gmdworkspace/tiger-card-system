package com.tigercard.fareengine.exception;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class PeakHourException extends Exception {
    public PeakHourException(String message, DayOfWeek dayOfWeek, LocalTime time) {
        super(message + " dayOfWeek:" + dayOfWeek + " timeOfTravel:" + time);
    }
}
