package com.tigercard.fareengine.config;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class PeakHourConfig {
    private static final Map<DayOfWeek, Map<LocalTime, LocalTime>> peakHourMap = new HashMap<>();

    public PeakHourConfig() {
        initPeakHoursMapping();
    }

    public Map<LocalTime, LocalTime> fetchPeakHoursForTheDay(DayOfWeek dayOfWeek) {
        if(dayOfWeek == null)
            return null;
        return peakHourMap.get(dayOfWeek);
    }

    private static void initPeakHoursMapping() {
        var weekDayPeakHours = new HashMap<LocalTime, LocalTime>();
        weekDayPeakHours.put(LocalTime.of(7, 0, 0), LocalTime.of(10, 30, 0));
        weekDayPeakHours.put(LocalTime.of(17, 0, 0), LocalTime.of(20, 0, 0));

        var weekEndPeakHours = new HashMap<LocalTime, LocalTime>();
        weekEndPeakHours.put(LocalTime.of(9, 0, 0), LocalTime.of(11, 0, 0));
        weekEndPeakHours.put(LocalTime.of(18, 0, 0), LocalTime.of(22, 0, 0));

        peakHourMap.put(DayOfWeek.MONDAY, weekDayPeakHours);
        peakHourMap.put(DayOfWeek.TUESDAY, weekDayPeakHours);
        peakHourMap.put(DayOfWeek.WEDNESDAY, weekDayPeakHours);
        peakHourMap.put(DayOfWeek.THURSDAY, weekDayPeakHours);
        peakHourMap.put(DayOfWeek.FRIDAY, weekDayPeakHours);
        peakHourMap.put(DayOfWeek.SATURDAY, weekEndPeakHours);
        peakHourMap.put(DayOfWeek.SUNDAY, weekEndPeakHours);
    }
}
