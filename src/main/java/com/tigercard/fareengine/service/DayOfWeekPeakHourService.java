package com.tigercard.fareengine.service;

import com.tigercard.fareengine.api.PeakHourProvider;
import com.tigercard.fareengine.config.PeakHourConfig;
import com.tigercard.fareengine.domain.HourType;
import com.tigercard.fareengine.exception.PeakHourException;
import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Map;

import static com.tigercard.fareengine.util.DateTimeUtil.isTimeEqualAndInBetween;

@Slf4j
public class DayOfWeekPeakHourService implements PeakHourProvider {

    private PeakHourConfig peakHourConfig;

    public DayOfWeekPeakHourService() {
        peakHourConfig = new PeakHourConfig();
    }

    @Override
    public HourType getHourType(DayOfWeek dayOfWeek, LocalTime timeOfTravel) throws PeakHourException {
        Map<LocalTime, LocalTime> peakHours = peakHourConfig.fetchPeakHoursForTheDay(dayOfWeek);
        if (peakHours == null || timeOfTravel == null) {
            log.error("Unable to fetch Hour Type for dayOfWeek:" + dayOfWeek + " timeOfTravel:" + timeOfTravel);
            throw new PeakHourException("Peak hours or timeOfTravel cannot be null", dayOfWeek, timeOfTravel);
        }
        return peakHours.entrySet()
                .stream()
                .filter(t -> isTimeEqualAndInBetween(t.getKey(), t.getValue(), timeOfTravel))
                .count() > 0 ? HourType.PEAK_HOUR : HourType.OFF_PEAK_HOUR;
    }
}
