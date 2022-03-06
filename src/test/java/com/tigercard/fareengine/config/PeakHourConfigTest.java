package com.tigercard.fareengine.config;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PeakHourConfigTest {
    private static PeakHourConfig peakHourConfig;

    @BeforeAll
    public static void setup() {
        peakHourConfig = new PeakHourConfig();
    }

    @Test
    public void shouldFetchPeakHoursForTheDay() {
        List<DayOfWeek> weekdays = List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);
        for (DayOfWeek dayOfWeek : weekdays) {
            assertEquals(LocalTime.of(10, 30, 0), peakHourConfig.fetchPeakHoursForTheDay(dayOfWeek).get(LocalTime.of(7, 0, 0)));
            assertEquals(LocalTime.of(20, 0, 0), peakHourConfig.fetchPeakHoursForTheDay(dayOfWeek).get(LocalTime.of(17, 0, 0)));
        }

        List<DayOfWeek> weekEnds = List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
        for (DayOfWeek weekEndDayOfWeek : weekEnds) {
            assertEquals(LocalTime.of(11, 0, 0), peakHourConfig.fetchPeakHoursForTheDay(weekEndDayOfWeek).get(LocalTime.of(9, 0, 0)));
            assertEquals(LocalTime.of(22, 0, 0), peakHourConfig.fetchPeakHoursForTheDay(weekEndDayOfWeek).get(LocalTime.of(18, 0, 0)));
        }
    }

    @Test
    public void shouldReturnNullIfDayOfWeekIsNull() {
        assertNull(peakHourConfig.fetchPeakHoursForTheDay(null));
    }
}
