package com.tigercard.fareengine.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.tigercard.fareengine.util.DateTimeUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DateTimeUtilTest {

    @Test
    public void shouldTestIsTimeEqualAndInBetween() {
        assertEquals(true, isTimeEqualAndInBetween(LocalTime.of(7, 0), LocalTime.of(8, 0), LocalTime.of(7, 0)));
        assertEquals(true, isTimeEqualAndInBetween(LocalTime.of(7, 0), LocalTime.of(8, 0), LocalTime.of(8, 0)));
        assertEquals(true, isTimeEqualAndInBetween(LocalTime.of(7, 0), LocalTime.of(8, 0), LocalTime.of(7, 30)));
        assertEquals(false, isTimeEqualAndInBetween(LocalTime.of(7, 0), LocalTime.of(8, 0), LocalTime.of(10, 30)));
    }

    @Test
    public void shouldTestIsTimeEqualAndInBetweenToThrowException() {
        assertThrows(Exception.class, () -> isTimeEqualAndInBetween(null, null, null));
        assertThrows(Exception.class, () -> isTimeEqualAndInBetween(LocalTime.of(7, 0), null, null));
        assertThrows(Exception.class, () -> isTimeEqualAndInBetween(null, LocalTime.of(8, 0), null));
        assertThrows(Exception.class, () -> isTimeEqualAndInBetween(LocalTime.of(7, 0), LocalTime.of(8, 0), null));
    }

    @Test
    public void shouldTestGetDateTime() {
        assertEquals(LocalDateTime.of(2022, 02, 28, 07, 20), getDateTime("2022-02-28 07:20"));
    }

    @Test
    public void shouldTestGetDateTimeThrowsException() {
        assertThrows(Exception.class, () -> getDateTime(""));
        assertThrows(Exception.class, () -> getDateTime(null));
    }

    @Test
    public void shouldTestGetWeekOfYear() throws Exception {
        assertEquals(10, getWeekOfYear(LocalDate.of(2022, 03, 03)));
        assertEquals(11, getWeekOfYear(LocalDate.of(2022, 03, 10)));
    }

    @Test
    public void shouldTestGetWeekOfYearThrowsException() {
        assertThrows(Exception.class, () -> getWeekOfYear(null));
        assertThrows(Exception.class, () -> getWeekOfYear(LocalDate.of(0, 0, 0)));
    }
}
