package com.tigercard.fareengine.service;

import com.tigercard.fareengine.api.PeakHourProvider;
import com.tigercard.fareengine.domain.HourType;
import com.tigercard.fareengine.exception.PeakHourException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DayOfWeekPeakHourServiceTest {
    private static PeakHourProvider peakHourProvider;

    @BeforeAll
    public static void setup() {
        peakHourProvider = new DayOfWeekPeakHourService();
    }

    @Test
    public void shouldGetHourType() throws PeakHourException {
        assertEquals(HourType.PEAK_HOUR, peakHourProvider.getHourType(DayOfWeek.MONDAY, LocalTime.of(7, 0)));
        assertEquals(HourType.PEAK_HOUR, peakHourProvider.getHourType(DayOfWeek.MONDAY, LocalTime.of(8, 0)));
        assertEquals(HourType.PEAK_HOUR, peakHourProvider.getHourType(DayOfWeek.MONDAY, LocalTime.of(10, 30)));
        assertEquals(HourType.PEAK_HOUR, peakHourProvider.getHourType(DayOfWeek.SATURDAY, LocalTime.of(9, 0)));
        assertEquals(HourType.PEAK_HOUR, peakHourProvider.getHourType(DayOfWeek.SATURDAY, LocalTime.of(10, 30)));
        assertEquals(HourType.PEAK_HOUR, peakHourProvider.getHourType(DayOfWeek.SATURDAY, LocalTime.of(11, 0)));

        assertEquals(HourType.OFF_PEAK_HOUR, peakHourProvider.getHourType(DayOfWeek.MONDAY, LocalTime.of(10, 31)));
        assertEquals(HourType.OFF_PEAK_HOUR, peakHourProvider.getHourType(DayOfWeek.MONDAY, LocalTime.of(16, 59)));
        assertEquals(HourType.OFF_PEAK_HOUR, peakHourProvider.getHourType(DayOfWeek.MONDAY, LocalTime.of(20, 1)));
        assertEquals(HourType.OFF_PEAK_HOUR, peakHourProvider.getHourType(DayOfWeek.SATURDAY, LocalTime.of(8, 59)));
        assertEquals(HourType.OFF_PEAK_HOUR, peakHourProvider.getHourType(DayOfWeek.SATURDAY, LocalTime.of(11, 1)));
        assertEquals(HourType.OFF_PEAK_HOUR, peakHourProvider.getHourType(DayOfWeek.SATURDAY, LocalTime.of(17, 59)));
        assertEquals(HourType.OFF_PEAK_HOUR, peakHourProvider.getHourType(DayOfWeek.SATURDAY, LocalTime.of(22, 1)));
    }

    @Test
    public void shouldThrowExceptionIfDayOfWeekOrTravelTimeIsNull() throws PeakHourException {
        assertThrows(PeakHourException.class, () -> peakHourProvider.getHourType(null, null));
        assertThrows(PeakHourException.class, () -> peakHourProvider.getHourType(null, LocalTime.of(7, 0)));
        assertThrows(PeakHourException.class, () -> peakHourProvider.getHourType(DayOfWeek.MONDAY, null));
    }
}
