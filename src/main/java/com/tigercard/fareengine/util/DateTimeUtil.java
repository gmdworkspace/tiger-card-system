package com.tigercard.fareengine.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class DateTimeUtil {
    public static Boolean isTimeEqualAndInBetween(LocalTime start, LocalTime end, LocalTime assertTime) throws DateTimeException {
        if (assertTime == null || start == null || end == null)
            throw new DateTimeException("assertTime param required for the check");
        return assertTime.equals(start) || assertTime.equals(end) || assertTime.isAfter(start) && assertTime.isBefore(end);
    }

    public static LocalDateTime getDateTime(String str) throws DateTimeException {
        if (str == null || str.isBlank())
            throw new DateTimeException("String param required to parse to LocalDateTime");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(str, formatter);
    }

    public static Integer getWeekOfYear(LocalDate localDate) throws DateTimeException {
        if (localDate == null)
            throw new DateTimeException("localDate param should have a proper value");
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }
}
